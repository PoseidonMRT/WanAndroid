package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.recommend.sevice.RecommendDataService
import com.poseidon.blc.recommend.usecase.RecommendDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class RecommendDataModule {

    @Provides
    fun getRecommendService(retrofit: Retrofit): RecommendDataService {
        return retrofit.create(RecommendDataService::class.java)
    }

    @Provides
    fun getRecommendUseCase(recommendDataService: RecommendDataService): RecommendDataUseCase {
        return RecommendDataUseCase(recommendDataService = recommendDataService)
    }
}