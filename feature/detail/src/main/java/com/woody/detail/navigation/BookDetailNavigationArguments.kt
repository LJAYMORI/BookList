package com.woody.detail.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookDetailNavigationArguments(
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