package com.poseidon.blc.square.entities

import com.poseidon.blc.common.ArticleBean

data class SquareResultData(

    val curPage: Int,
    val datas: List<ArticleBean>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)