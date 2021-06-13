package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.home.service.HomeDataService
import com.poseidon.blc.home.usecase.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class HomeModule {
    @Provides
    fun getHomeService(retrofit: Retrofit): HomeDataService {
        return retrofit.create(HomeDataService::class.java)
    }

    @Provides
    fun getHomeUseCase(homeDataService: HomeDataService): HomeUseCase {
        return HomeUseCase(homeDataService = homeDataService)
    }
}