package com.poseidon.network.imageloader

import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso

interface ImageLoadCallback<T> {
    fun onSuccessLoaded(result: T?, from: Picasso.LoadedFrom?)
    fun onFailedLoaded(e: Exception?, errorDrawable: Drawable?)
}