package com.poseidon.blc.wechat.usecase

import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import com.poseidon.blc.wechat.service.WechatDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WechatUseCase @Inject constructor(var wechatDataService: WechatDataService) {
    fun getWechatOfficialList(useCaseRequestCallback: UseCaseRequestCallback<OfficialWechatListBeans>) {
        wechatDataService.getOfficialWechatList()
            .enqueue(object : Callback<OfficialWechatListBeans> {
                override fun onResponse(
                    call: Call<OfficialWechatListBeans>,
                    response: Response<OfficialWechatListBeans>
                ) {
                    if (response.isSuccessful) {
                        var officialWechatListBeans = response.body()
                        if (officialWechatListBeans != null) {
                            useCaseRequestCallback.onSuccess(officialWechatListBeans)
                        } else {
                            useCaseRequestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA, "officialWechatListBeans is null"
                                )
                            )
                        }
                    } else {
                        useCaseRequestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                response.code(), "getOfficialWechatList request failed"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<OfficialWechatListBeans>, t: Throwable) {
                    useCaseRequestCallback.onError(call, t)
                }
            })
    }
}