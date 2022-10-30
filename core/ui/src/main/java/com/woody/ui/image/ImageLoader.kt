package com.woody.ui.image

import android.annotation.SuppressLint
import android.app.Application
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageLoader {
    fun onLowMemory(application: Application) {
        Glide.get(application).onLowMemory()
    }

    fun onTrimMemory(application: Application, level: Int) {
        Glide.get(application).onTrimMemory(level)
    }

    fun clearRequestImage(imageView: ImageView) {
        Glide.with(imageView).clear(imageView)
    }
}

@SuppressLint("CheckResult")
fun ImageView.loadUrl(
    url: String,
    @DrawableRes placeholderResId: Int? = null,
    width: Int? = null,
    height: Int? = null
) {
    Glide.with(this)
        .load(url)
        .also { builder ->
            val requestOption = RequestOptions()
            if (placeholderResId != null) {
                requestOption.placeholder(placeholderResId)
            }
            if (width != null && height != null) {
                requestOption.override(width, height)
            }
            builder.apply(requestOption)
        }
        .into(this)
}