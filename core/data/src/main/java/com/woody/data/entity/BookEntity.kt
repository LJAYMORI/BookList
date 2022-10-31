package com.woody.data.entity

import com.woody.database.entity.BookmarkedBook
import com.woody.network.vo.BookVO

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
)

fun BookEntity.toBookmarkedBook(): BookmarkedBook {
    return BookmarkedBook(
        title = title,
        author = author,
        isbn = isbn,
        price = price,
        image = image,
        publisher = publisher,
        pubdate = pubdate,
        discount = discount,
        description = description
    )
}

fun BookmarkedBook.toEntity(): BookEntity {
    return BookEntity(
        title = title,
        author = author,
        isbn = isbn,
        price = price,
        image = image,
        publisher = publisher,
        pubdate = pubdate,
        discount = discount,
        description = description
    )
}

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