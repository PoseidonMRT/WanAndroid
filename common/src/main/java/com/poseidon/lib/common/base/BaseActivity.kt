package com.poseidon.lib.common.base

import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initObservers()
    abstract fun initData()
}