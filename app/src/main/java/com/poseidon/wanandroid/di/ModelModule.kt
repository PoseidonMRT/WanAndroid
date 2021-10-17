package com.poseidon.wanandroid.di

import com.poseidon.blc.banner.usecase.BannerUseCase
import com.poseidon.blc.home.usecase.HomeUseCase
import com.poseidon.blc.recommend.usecase.RecommendDataUseCase
import com.poseidon.wanandroid.business.main.MainModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ModelModule {
    @Provides
    fun getMainModel(
        bannerUseCase: BannerUseCase,
        recommendDataUseCase: RecommendDataUseCase,
        homeUseCase: HomeUseCase
    ): MainModel {
        return MainModel(
            bannerUseCase = bannerUseCase,
            recommendDataUseCase = recommendDataUseCase,
            homeUseCase = homeUseCase
        )
    }
}