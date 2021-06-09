package com.poseidon.blc.knowledge.service

import com.poseidon.blc.knowledge.entities.ArticleListInKnowledgeBean
import com.poseidon.blc.knowledge.entities.KnowledgeListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface KnowledgeService {
    @GET("tree/json")
    fun getKnowledgeList(): Call<KnowledgeListBean>

    @GET("article/list/{pageIndex}/json?cid={cid}")
    fun getArticleListByKnowledgeTypeAndPageIndex(
        @Path("pageIndex") pageIndex: Int,
        @Path("cid") cid: Int
    ): Call<ArticleListInKnowledgeBean>
}