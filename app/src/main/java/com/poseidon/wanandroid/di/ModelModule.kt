package com.poseidon.wanandroid.di

import com.poseidon.blc.banner.usecase.BannerUseCase
import com.poseidon.blc.recommend.usecase.RecommendDataUseCase
import com.poseidon.wanandroid.main.MainModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ModelModule {
    @Provides
    fun getMainModel(
        bannerUseCase: BannerUseCase,
        recommendDataUseCase: RecommendDataUseCase
    ): MainModel {
        return MainModel(bannerUseCase = bannerUseCase, recommendDataUseCase = recommendDataUseCase)
    }
}