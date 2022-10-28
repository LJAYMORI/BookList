package com.woody.module_data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookEntity(
    val title: String,
    val author: String,
    val isbn: String,
    val price: String,
    val image: String,
    val publisher: String,
    val pubdate: String,
    val discount: String,
    val description: String
) : Parcelable