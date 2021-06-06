package com.poseidon.blc.banner.service

import com.poseidon.blc.banner.entities.BannerBeans
import retrofit2.Call
import retrofit2.http.GET

interface BannerDataService {
    @GET("banner/json")
    fun getBannerData(): Call<BannerBeans>
}