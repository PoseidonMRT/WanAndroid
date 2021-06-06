package com.poseidon.wanandroid.di

import com.poseidon.blc.banner.service.BannerDataService
import com.poseidon.blc.banner.usecase.BannerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class BannerModule {

    @Provides
    fun getBannerDataService(retrofit: Retrofit): BannerDataService =
        retrofit.create(BannerDataService::class.java)

    @Provides
    fun getBannerUseCase(bannerDataService: BannerDataService): BannerUseCase {
        return BannerUseCase(bannerDataService)
    }
}