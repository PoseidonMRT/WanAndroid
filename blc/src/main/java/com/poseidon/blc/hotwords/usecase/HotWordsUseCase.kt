package com.poseidon.blc.hotwords.usecase

import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.hotwords.entities.HotWordsListBean
import com.poseidon.blc.hotwords.service.HotWordsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HotWordsUseCase @Inject constructor(var hotWordsService: HotWordsService) {
    fun getHotWordsList(requestCallback: UseCaseRequestCallback<HotWordsListBean>) {
        hotWordsService.getHotWordsList().enqueue(object : Callback<HotWordsListBean> {
            override fun onResponse(
                call: Call<HotWordsListBean>,
                response: Response<HotWordsListBean>
            ) {
                if (response.isSuccessful) {
                    var hotWordsListBean = response.body()
                    if (hotWordsListBean != null) {
                        requestCallback.onSuccess(hotWordsListBean)
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "hotWordsListBean beans is null"
                            )
                        )
                    }
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getHotWordsList request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<HotWordsListBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }
}