package com.poseidon.blc.wechat.usecase

import android.util.Log
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
    val tag = "WechatUseCase"
    fun getWechatOfficialList(useCaseRequestCallback: UseCaseRequestCallback<OfficialWechatListBeans>) {
        wechatDataService.getOfficialWechatList()
            .enqueue(object : Callback<OfficialWechatListBeans> {
                override fun onResponse(
                    call: Call<OfficialWechatListBeans>,
                    response: Response<OfficialWechatListBeans>
                ) {
                    if (response.isSuccessful) {
                        var wechatListBeans = response.body()
                        if (wechatListBeans == null) {
                            useCaseRequestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA, "getTreeList beans is null"
                                )
                            )
                            return
                        }

                        Log.d(tag, wechatListBeans.toString())
                        if (wechatListBeans.errorCode != 0) {
                            useCaseRequestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    wechatListBeans.errorCode, wechatListBeans.errorMsg
                                )
                            )
                            return
                        }

                        useCaseRequestCallback.onSuccess(wechatListBeans)
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