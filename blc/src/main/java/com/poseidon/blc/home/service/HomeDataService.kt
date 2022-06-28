package com.poseidon.blc.home.service

import com.poseidon.blc.home.entities.HomeArticleListBean
import com.poseidon.blc.home.entities.HotArticleListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeDataService {
    @GET("article/list/{pageIndex}/json")
    fun getHomeArticleList(@Path("pageIndex") pageIndex: Int): Call<HomeArticleListBean>

    @GET("article/top/json")
    fun getHotArticleList(): Call<HotArticleListBean>
}