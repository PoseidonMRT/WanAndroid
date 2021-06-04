package com.poseidon.wanandroid.wechat.usecase

import android.util.Log
import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import com.poseidon.blc.wechat.service.WechatDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WechatUseCase @Inject constructor(var wechatDataService: WechatDataService) {
    fun getWechatOfficialList() {
        wechatDataService.getOfficialWechatList()
            .enqueue(object : Callback<OfficialWechatListBeans> {
                override fun onResponse(
                    call: Call<OfficialWechatListBeans>,
                    response: Response<OfficialWechatListBeans>
                ) {
                    if (response.isSuccessful) {
                        var officialWechatListBeans = response.body()
                        Log.d("ZBTUO", officialWechatListBeans.toString())
                    }

                }

                override fun onFailure(call: Call<OfficialWechatListBeans>, t: Throwable) {
                    Log.d("ZBTUO", t.toString())
                }
            })
    }
}