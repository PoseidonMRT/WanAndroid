package com.poseidon.blc.square.service

import com.poseidon.blc.square.entities.SquareResultBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SquareDataService {
    @GET("user_article/list/{pageIndex}/json")
    fun getSquareData(@Path("pageIndex") pageIndex: Int): Call<SquareResultBean>
}