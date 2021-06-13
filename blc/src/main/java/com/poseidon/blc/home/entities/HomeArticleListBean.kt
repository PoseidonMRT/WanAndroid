package com.poseidon.blc.home.entities

import com.poseidon.blc.common.ArticleBean

data class HomeArticleListBean(
    val data: DataBean,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val curPage: Int,
        val datas: List<ArticleBean>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
    ) {
    }
}