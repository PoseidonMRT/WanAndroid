package com.poseidon.wanandroid.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import com.poseidon.blc.wechat.usecase.WechatUseCase
import retrofit2.Call

class MainViewModel @ViewModelInject constructor(var mainModel: WechatUseCase) : ViewModel() {

    fun login() {
        mainModel.getWechatOfficialList(object : UseCaseRequestCallback<OfficialWechatListBeans> {
            override fun onSuccess(t: OfficialWechatListBeans) {
                TODO("Not yet implemented")
            }

            override fun onFailed(call: Call<OfficialWechatListBeans>, t: UseCaseRequestFailed) {
                TODO("Not yet implemented")
            }

            override fun onError(call: Call<OfficialWechatListBeans>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}