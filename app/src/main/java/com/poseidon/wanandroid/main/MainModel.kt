package com.poseidon.wanandroid.main

import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.banner.usecase.BannerUseCase
import com.poseidon.blc.callback.UseCaseRequestCallback
import javax.inject.Inject

class MainModel @Inject constructor(var bannerUseCase: BannerUseCase) {
    fun getBannerList(useCaseRequestCallback: UseCaseRequestCallback<BannerBeans>) {
        bannerUseCase.getBannerData(useCaseRequestCallback = useCaseRequestCallback)
    }
}