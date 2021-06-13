package com.poseidon.blc.recommend.sevice

import com.poseidon.blc.recommend.entities.RecommendArticleBean
import retrofit2.Call
import retrofit2.http.GET

interface RecommendDataService {
    @GET("article/top/json")
    fun getRecommendArticleList(): Call<RecommendArticleBean>
}