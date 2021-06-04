package com.poseidon.wanandroid.di

import com.poseidon.wanandroid.MainModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class UseCaseModule {

    @Provides
    fun provideMainModel(retrofit: Retrofit): MainModel {
        return MainModel(retrofit)
    }
}