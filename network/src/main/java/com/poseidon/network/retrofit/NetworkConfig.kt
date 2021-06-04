package com.poseidon.network.retrofit

import retrofit2.Converter
import retrofit2.CallAdapter
import okhttp3.Interceptor
import java.util.ArrayList

class NetworkConfig(var baseUrl: String) {
    var connectTimeOutMillis: Long = 3000
    var readTimeOutMillis: Long = 3000
    var writeTimeOutMillis: Long = 3000
    var convertFactory: Converter.Factory? = null
    var callAdapterFactory: CallAdapter.Factory? = null
    private var mInterceptors: MutableList<Interceptor>? = null
    val interceptors: List<Interceptor>?
        get() = mInterceptors

    fun setInterceptors(mInterceptors: MutableList<Interceptor>?) {
        this.mInterceptors = mInterceptors
    }

    fun addInterceptors(interceptor: Interceptor) {
        if (mInterceptors == null) {
            mInterceptors = ArrayList()
        }
        mInterceptors!!.add(interceptor)
    }
}