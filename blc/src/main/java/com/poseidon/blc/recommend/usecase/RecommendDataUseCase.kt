package com.poseidon.blc.recommend.usecase

import android.util.Log
import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.recommend.entities.RecommendArticleBean
import com.poseidon.blc.recommend.sevice.RecommendDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RecommendDataUseCase @Inject constructor(var recommendDataService: RecommendDataService) {
    val TAG = "RecommendDataUseCase"
    fun getRecommendArticleList(requestCallback: UseCaseRequestCallback<RecommendArticleBean>) {
        recommendDataService.getRecommendArticleList()
            .enqueue(object : Callback<RecommendArticleBean> {
                override fun onResponse(
                    call: Call<RecommendArticleBean>,
                    response: Response<RecommendArticleBean>
                ) {
                    if (response.isSuccessful) {
                        var recommendArticleBean = response.body()
                        if (recommendArticleBean == null) {
                            requestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA, "recommendArticleBean beans is null"
                                )
                            )
                            return
                        }

                        Log.d(TAG, recommendArticleBean.toString())
                        if (recommendArticleBean.errorCode != 0) {
                            requestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    recommendArticleBean.errorCode, recommendArticleBean.errorMsg
                                )
                            )
                            return
                        }

                        requestCallback.onSuccess(recommendArticleBean)
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                response.code(), "getRecommendArticleList request failed"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<RecommendArticleBean>, t: Throwable) {
                    requestCallback.onError(call, t)
                }
            })
    }
}