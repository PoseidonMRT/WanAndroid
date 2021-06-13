package com.poseidon.wanandroid.main

import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.banner.usecase.BannerUseCase
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.recommend.entities.RecommendArticleBean
import com.poseidon.blc.recommend.usecase.RecommendDataUseCase
import javax.inject.Inject

class MainModel @Inject constructor(
    var bannerUseCase: BannerUseCase,
    var recommendDataUseCase: RecommendDataUseCase
) {
    fun getBannerList(useCaseRequestCallback: UseCaseRequestCallback<BannerBeans>) {
        bannerUseCase.getBannerData(useCaseRequestCallback = useCaseRequestCallback)
    }

    fun getRecommendArticleList(requestCallback: UseCaseRequestCallback<RecommendArticleBean>) {
        recommendDataUseCase.getRecommendArticleList(requestCallback = requestCallback)
    }
}