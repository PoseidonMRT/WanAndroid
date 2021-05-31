package com.poseidon.wanandroid

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ViewModelModule {
    @Provides
    fun provideMainModel(): MainModel {
        return MainModel()
    }
}