package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.wechat.service.WechatDataService
import com.poseidon.blc.wechat.usecase.WechatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class WechatModule {

    @Provides
    fun provideWechatDataService(retrofit: Retrofit): WechatDataService {
        return retrofit.create(WechatDataService::class.java)
    }

    @Provides
    fun provideWechatUseCase(wechatDataService: WechatDataService): WechatUseCase {
        return WechatUseCase(wechatDataService)
    }
}