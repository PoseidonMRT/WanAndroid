package com.poseidon.blc.callback

import retrofit2.Call

interface UseCaseRequestCallback<T> {
    fun onSuccess(t: T)
    fun onFailed(call: Call<T>, t: UseCaseRequestFailed)
    fun onError(call: Call<T>, t: Throwable)
}