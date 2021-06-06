package com.poseidon.blc.callback

const val ERROR_CODE_ILLEGAL_DATA: Int = 100
const val ERROR_CODE_REQUEST_FAILED: Int = 999

data class UseCaseRequestFailed(val code: Int, val msg: String)
