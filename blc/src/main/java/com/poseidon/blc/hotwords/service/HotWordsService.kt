package com.poseidon.blc.hotwords.service

import com.poseidon.blc.hotwords.entities.HotWordsListBean
import retrofit2.Call
import retrofit2.http.GET

interface HotWordsService {
    @GET("hotkey/json")
    fun getHotWordsList(): Call<HotWordsListBean>
}