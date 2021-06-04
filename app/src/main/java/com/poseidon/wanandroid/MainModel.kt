package com.poseidon.wanandroid

import android.util.Log
import retrofit2.Retrofit
import javax.inject.Inject

class MainModel @Inject constructor(retrofit: Retrofit) {
    fun login() {
        Log.d("MainModel", "login called")
    }
}