package com.poseidon.blc.author.service

import com.poseidon.blc.author.entities.ArticleListByAuthorBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthorService {
    @GET("article/list/{pageIndex}/json?author={authorName}")
    fun getArticleListByAuthorAndPageIndex(
        @Path("pageIndex") pageIndex: Int,
        @Path("authorName") authorName: String
    ): Call<ArticleListByAuthorBean>
}