package com.poseidon.blc.banner.usecase

import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.banner.service.BannerDataService
import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestFailed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BannerUseCase @Inject constructor(var bannerDataService: BannerDataService) {
    fun getBannerData(useCaseRequestCallback: UseCaseRequestCallback<BannerBeans>) {
        bannerDataService.getBannerData().enqueue(object : Callback<BannerBeans> {
            override fun onResponse(call: Call<BannerBeans>, response: Response<BannerBeans>) {
                if (response.isSuccessful) {
                    var bannerBeans = response.body()
                    if (bannerBeans != null) {
                        useCaseRequestCallback.onSuccess(bannerBeans)
                    } else {
                        useCaseRequestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "banner beans is null"
                            )
                        )
                    }
                } else {
                    useCaseRequestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getBannerData request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<BannerBeans>, t: Throwable) {
                useCaseRequestCallback.onError(call, t)
            }
        })
    }
}