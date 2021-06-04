package com.poseidon.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private fun getOkHttpClient(networkConfig: NetworkConfig): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(networkConfig.connectTimeOutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(networkConfig.readTimeOutMillis, TimeUnit.MILLISECONDS)
            .writeTimeout(networkConfig.writeTimeOutMillis, TimeUnit.MILLISECONDS)
        networkConfig.interceptors!!.forEach { it ->
            builder.addInterceptor(it)
        }
        return builder.build()
    }

    fun getRetrofit(networkConfig: NetworkConfig): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(networkConfig.baseUrl)
            .client(getOkHttpClient(networkConfig))
        if (networkConfig.convertFactory != null) {
            builder.addConverterFactory(networkConfig.convertFactory)
        }

        if (networkConfig.callAdapterFactory != null) {
            builder.addCallAdapterFactory(networkConfig.callAdapterFactory!!)
        }

        return builder.build()
    }
}