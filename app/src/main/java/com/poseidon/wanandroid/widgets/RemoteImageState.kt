package com.poseidon.wanandroid.widgets

import android.graphics.drawable.BitmapDrawable

sealed class RemoteImageState {

    object Loading : RemoteImageState()

    data class Loaded(
        val image: BitmapDrawable
    ) : RemoteImageState()

    object LoadError : RemoteImageState()

}