package com.poseidon.lib.common.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private fun getOkHttpClient(networkConfig: NetworkConfig): OkHttpClient {
        var builder = OkHttpClient.Builder()
            .connectTimeout(networkConfig.connectTimeOutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(networkConfig.readTimeOutMillis, TimeUnit.MILLISECONDS)
            .writeTimeout(networkConfig.writeTimeOutMillis, TimeUnit.MILLISECONDS);
        if (networkConfig.interceptors.isNotEmpty()) {
            networkConfig.interceptors.forEach { it ->
                builder.addInterceptor(it)
            }
        }
        return builder.build()
    }

    fun getRetrofit(networkConfig: NetworkConfig): Retrofit {
        var builder = Retrofit.Builder()
        builder.baseUrl(networkConfig.baseUrl)
            .client(getOkHttpClient(networkConfig))
        if (networkConfig.convertFactory != null) {
            builder.addConverterFactory(networkConfig.convertFactory)
        }
        if (networkConfig.callAdapterFactory != null) {
            builder.addCallAdapterFactory(networkConfig.callAdapterFactory)
        }
        return builder.build()
    }
}