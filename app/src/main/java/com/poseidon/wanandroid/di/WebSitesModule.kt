package com.poseidon.wanandroid.di

import com.poseidon.blc.website.service.WebSitesService
import com.poseidon.blc.website.usecase.WebSitesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class WebSitesModule {
    @Provides
    fun getWebSitesService(retrofit: Retrofit): WebSitesService {
        return retrofit.create(WebSitesService::class.java)
    }

    @Provides
    fun getWebSitesUseCase(webSitesService: WebSitesService): WebSitesUseCase {
        return WebSitesUseCase(webSitesService = webSitesService)
    }
}