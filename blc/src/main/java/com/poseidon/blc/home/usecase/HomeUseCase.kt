package com.poseidon.blc.home.usecase

import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.home.entities.HomeArticleListBean
import com.poseidon.blc.home.service.HomeDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeUseCase @Inject constructor(var homeDataService: HomeDataService) {
    fun getHomeArticleList(
        pageIndex: Int,
        requestCallback: UseCaseRequestCallback<HomeArticleListBean>
    ) {
        homeDataService.getHomeArticleList(pageIndex)
            .enqueue(object : Callback<HomeArticleListBean> {
                override fun onResponse(
                    call: Call<HomeArticleListBean>,
                    response: Response<HomeArticleListBean>
                ) {
                    if (response.isSuccessful) {
                        var homeArticleListBean = response.body()
                        if (homeArticleListBean != null) {
                            requestCallback.onSuccess(homeArticleListBean)
                        } else {
                            requestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA, "homeArticleListBean beans is null"
                                )
                            )
                        }
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                response.code(), "getHomeArticleList request failed"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<HomeArticleListBean>, t: Throwable) {
                    requestCallback.onError(call, t)
                }
            })
    }
}