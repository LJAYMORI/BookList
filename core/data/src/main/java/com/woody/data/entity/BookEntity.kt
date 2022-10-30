package com.woody.data.entity

import android.os.Parcelable
import com.woody.network.vo.BookVO
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

fun BookVO.toEntity(): BookEntity {
    return BookEntity(
        title = title ?: "",
        author = author ?: "",
        isbn = isbn ?: "",
        price = price ?: "",
        image = image ?: "",
        publisher = publisher ?: "",
        pubdate = pubdate ?: "",
        discount = discount ?: "",
        description = description ?: ""
    )
}