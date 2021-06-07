package com.poseidon.lib.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.annotations.NotNull

abstract class BaseViewModel<T : ViewState> : ViewModel() {
    var viewState: MutableLiveData<T> = MutableLiveData<T>()

    init {
        getViewState().also { viewState.value = it }
    }

    @NotNull
    abstract fun getViewState(): T
}