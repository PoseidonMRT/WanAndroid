package com.poseidon.wanandroid.di

import com.google.gson.Gson
import com.poseidon.network.retrofit.NetworkConfig
import com.poseidon.network.retrofit.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getGson(): Gson {
        return Gson()
    }

    @Provides
    fun getNetworkConfig(gson: Gson): NetworkConfig {
        val networkConfig = NetworkConfig("https://www.wanandroid.com")
        networkConfig.convertFactory = GsonConverterFactory.create(gson)
        return networkConfig
    }

    @Provides
    @Singleton
    fun getRetrofit(networkConfig: NetworkConfig): Retrofit {
        return RetrofitManager.getRetrofit(networkConfig = networkConfig)
    }
}