package com.poseidon.blc.recommend.entities

import com.poseidon.blc.common.ArticleBean

data class RecommendArticleBean(
    val data: List<ArticleBean>,
    val errorCode: Int,
    val errorMsg: String
) {
    override fun toString(): String {
        return "RecommendArticleBean(data=$data, errorCode=$errorCode, errorMsg='$errorMsg')"
    }
}