package com.poseidon.wanandroid.wechat.di

import com.poseidon.blc.wechat.service.WechatDataService
import com.poseidon.wanandroid.wechat.usecase.WechatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
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