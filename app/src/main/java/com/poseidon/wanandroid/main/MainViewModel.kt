package com.poseidon.wanandroid.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.poseidon.wanandroid.wechat.usecase.WechatUseCase

class MainViewModel @ViewModelInject constructor(var mainModel: WechatUseCase) : ViewModel() {

    fun login() {
        mainModel.getWechatOfficialList()
    }
}