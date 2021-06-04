package com.poseidon.wanandroid.di

import com.poseidon.network.retrofit.NetworkConfig
import com.poseidon.network.retrofit.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun getNetworkConfig(): NetworkConfig {
        return NetworkConfig("https://www.wanandroid.com")
    }

    @Provides
    fun getRetrofit(networkConfig: NetworkConfig): Retrofit {
        return RetrofitManager.getRetrofit(networkConfig = networkConfig)
    }
}