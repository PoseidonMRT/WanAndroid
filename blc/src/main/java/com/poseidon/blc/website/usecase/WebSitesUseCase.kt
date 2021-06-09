package com.poseidon.blc.website.usecase

import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.website.entities.WebSitesBean
import com.poseidon.blc.website.service.WebSitesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WebSitesUseCase @Inject constructor(var webSitesService: WebSitesService) {
    fun getWebSitesList(requestCallback: UseCaseRequestCallback<WebSitesBean>) {
        webSitesService.getWebSitesList().enqueue(object : Callback<WebSitesBean> {
            override fun onResponse(call: Call<WebSitesBean>, response: Response<WebSitesBean>) {
                if (response.isSuccessful) {
                    var webSitesBean = response.body()
                    if (webSitesBean != null) {
                        requestCallback.onSuccess(webSitesBean)
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "webSitesBean beans is null"
                            )
                        )
                    }
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getWebSitesList request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<WebSitesBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }
}