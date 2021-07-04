package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.hotwords.service.HotWordsService
import com.poseidon.blc.hotwords.usecase.HotWordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class HotWordsModule {

    @Provides
    fun getHotWordsService(retrofit: Retrofit): HotWordsService {
        return retrofit.create(HotWordsService::class.java)
    }

    @Provides
    fun getHotWordsUseCase(hotWordsService: HotWordsService): HotWordsUseCase {
        return HotWordsUseCase(hotWordsService = hotWordsService)
    }
}