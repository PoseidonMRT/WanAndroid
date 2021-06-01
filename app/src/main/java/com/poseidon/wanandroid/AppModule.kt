package com.poseidon.wanandroid

import com.poseidon.lib.common.network.RetrofitManager
import com.poseidon.lib.common.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun getRetrofit(): Retrofit {
        return RetrofitManager.getRetrofit(getNetworkConfig())
    }

    private fun getNetworkConfig(): NetworkConfig {
        return NetworkConfig(baseUrl = "http")
    }
}