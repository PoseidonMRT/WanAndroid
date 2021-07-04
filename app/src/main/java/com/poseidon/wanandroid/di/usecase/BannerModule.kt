package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.banner.service.BannerDataService
import com.poseidon.blc.banner.usecase.BannerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class BannerModule {

    @Provides
    fun getBannerDataService(retrofit: Retrofit): BannerDataService =
        retrofit.create(BannerDataService::class.java)

    @Provides
    fun getBannerUseCase(bannerDataService: BannerDataService): BannerUseCase {
        return BannerUseCase(bannerDataService)
    }
}