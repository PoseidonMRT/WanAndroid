package com.poseidon.wanandroid.widgets

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.imageloading.rememberDrawablePainter
import com.poseidon.network.imageloader.ImageLoadCallback
import com.poseidon.network.imageloader.ImageLoader
import com.squareup.picasso.Picasso


@Composable
fun RemoteImage(
    url: String?,
    modifier: Modifier,
    rotate: Float = 0f,
    error: @Composable (() -> Unit)? = null,
    loading: @Composable (() -> Unit?)? = null
) {
    val state = loadImage(url = url, rotate = rotate)
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,

        ) {
        when (state) {
            is RemoteImageState.Loading -> {
                if (loading != null) {
                    loading()
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            is RemoteImageState.Loaded -> {
                Image(
                    painter = rememberDrawablePainter(drawable = state.image),
                    contentDescription = ""
                )
            }
            is RemoteImageState.LoadError -> {
                if (error != null) {
                    error()
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Could not load image")
                    }
                }
            }
        }
    }
}


@Composable
fun loadImage(
    url: String?,
    rotate: Float,
): RemoteImageState {
    var state by remember(url) {
        mutableStateOf<RemoteImageState>(RemoteImageState.Loading)
    }
    if (url.isNullOrEmpty()) {
        state = RemoteImageState.LoadError
    } else {
        ImageLoader.loadImageAsBitmapDrawable(
            url,
            rotate,
            object : ImageLoadCallback<BitmapDrawable> {
                override fun onSuccessLoaded(result: BitmapDrawable?, from: Picasso.LoadedFrom?) {
                    if (result != null) {
                        state = RemoteImageState.Loaded(result)
                    } else {
                        state = RemoteImageState.LoadError
                    }
                }

                override fun onFailedLoaded(e: Exception?, errorDrawable: Drawable?) {
                    state = RemoteImageState.LoadError
                }
            })
    }
    return state
}
