package com.poseidon.blc.website.service

import com.poseidon.blc.website.entities.WebSitesBean
import retrofit2.Call
import retrofit2.http.GET

interface WebSitesService {
    @GET("friend/json")
    fun getWebSitesList(): Call<WebSitesBean>
}