package com.poseidon.blc.wechat.entities

data class SearchHistoryOfficialWechatBean(
    val data: DataBean,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val curPage: Int,
        val datas: List<Any?>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
    ) {

    }
}