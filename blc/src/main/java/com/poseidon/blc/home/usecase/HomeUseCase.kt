package com.poseidon.blc.home.usecase

import android.util.Log
import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.home.entities.HomeArticleListBean
import com.poseidon.blc.home.entities.HotArticleListBean
import com.poseidon.blc.home.service.HomeDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeUseCase @Inject constructor(var homeDataService: HomeDataService) {

    val tag = "HomeUseCase"
    fun getTopHotArticleList(requestCallback: UseCaseRequestCallback<HotArticleListBean>) {
        homeDataService.getHotArticleList().enqueue(object : Callback<HotArticleListBean> {
            override fun onResponse(
                call: Call<HotArticleListBean>,
                response: Response<HotArticleListBean>
            ) {
                if (response.isSuccessful) {
                    var hotArticleListBean = response.body()
                    Log.d(tag, hotArticleListBean.toString())
                    if (hotArticleListBean != null) {
                        if (hotArticleListBean.errorCode == 0) {
                            requestCallback.onSuccess(hotArticleListBean)
                        }
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "hotArticleListBean beans is null"
                            )
                        )
                    }
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getTopHotArticleList request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<HotArticleListBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }

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
                        Log.d(tag, homeArticleListBean.toString())
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