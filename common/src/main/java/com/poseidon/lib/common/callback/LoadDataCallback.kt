package com.poseidon.lib.common.callback

interface LoadDataCallback<T> {
    fun onSuccess(t: T)
    fun onFailed(code: Int, msg: String)
}