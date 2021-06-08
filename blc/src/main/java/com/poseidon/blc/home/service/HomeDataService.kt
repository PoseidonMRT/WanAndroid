package com.poseidon.blc.home.service

import com.poseidon.blc.home.entities.HomeArticleListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeDataService {
    @GET("article/list/{pageIndex}/json")
    fun getHomeArticleList(@Path("pageIndex") pageIndex: Int): Call<HomeArticleListBean>
}