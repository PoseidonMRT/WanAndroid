package com.poseidon.lib.common.network

import okhttp3.Interceptor
import org.jetbrains.annotations.NotNull
import retrofit2.CallAdapter
import retrofit2.Converter

const val defaultConnectTimeOutMillis: Long = 3000
const val defaultReadTimeOutMillis: Long = 3000
const val defaultWriteTimeOutMillis: Long = 3000

data class NetworkConfig(
    var connectTimeOutMillis: Long = defaultConnectTimeOutMillis,
    var readTimeOutMillis: Long = defaultReadTimeOutMillis,
    var writeTimeOutMillis: Long = defaultWriteTimeOutMillis,
    @NotNull var baseUrl: String,
    var convertFactory: Converter.Factory? = null,
    var callAdapterFactory: CallAdapter.Factory? = null,
    var interceptors: MutableList<Interceptor> = mutableListOf<Interceptor>()
)
