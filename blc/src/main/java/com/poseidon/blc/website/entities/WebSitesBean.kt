package com.poseidon.blc.website.entities

data class WebSitesBean(
    val data: List<DataBean>,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val category: String,
        val icon: String,
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
    )
}