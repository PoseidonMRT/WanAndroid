package com.poseidon.blc.hotwords.entities

data class HotWordsListBean(
    val data: List<DataBean>,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
    )
}