package com.poseidon.blc.wechat.service


import com.poseidon.blc.wechat.entities.OfficialWechatHistoryBean
import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import com.poseidon.blc.wechat.entities.SearchHistoryOfficialWechatBean
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
    ): Call<OfficialWechatHistoryBean>

    @GET("wxarticle/list/{id}}/{pageIndex}/json?k={keyWord}")
    fun getPageOfSearchOfficialWechatHistoryById(
        @Path("id") id: Int,
        @Path("pageIndex") pageIndex: Int,
        @Path("keyWord") keyWord: String
    ): Call<SearchHistoryOfficialWechatBean>
}