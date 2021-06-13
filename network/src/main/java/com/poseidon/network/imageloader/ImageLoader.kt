package com.poseidon.network.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ImageLoader {
    companion object {
        fun loadImageAsBitmapDrawable(
            url: String,
            rotate: Float,
            imageLoadCallback: ImageLoadCallback<BitmapDrawable>
        ) {
            Picasso.get().load(url).rotate(rotate).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    if (bitmap == null) {
                        onBitmapFailed(Exception("Incorrect url"), null)
                    } else {
                        imageLoadCallback.onSuccessLoaded(BitmapDrawable(bitmap), from)
                    }
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    e?.printStackTrace()
                    imageLoadCallback.onFailedLoaded(e, errorDrawable)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }
            })
        }
    }
}