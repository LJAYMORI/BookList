package com.woody.module_ui.image

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}