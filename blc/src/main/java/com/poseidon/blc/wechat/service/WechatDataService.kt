package com.poseidon.blc.wechat.service


import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WechatDataService {

    @GET("wxarticle/chapters/json")
    fun getOfficialWechatList(): Call<OfficialWechatListBeans>

    @GET("wxarticle/list/{id}/{pageIndex}}/json")
    fun getPageOfOfficialWechatHistoryById(
        @Path("id") id: Int,
        @Path("pageIndex") pageIndex: Int
    ): Call<ResponseBody>
}