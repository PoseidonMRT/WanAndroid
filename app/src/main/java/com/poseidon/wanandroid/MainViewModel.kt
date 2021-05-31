package com.poseidon.wanandroid

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class MainViewModel @ViewModelInject constructor(var mainModel: MainModel) : ViewModel() {

    fun login() {
        mainModel.login()
    }
}