package com.woody.data.mapper

import com.woody.database.entity.BookEntity
import com.woody.database.entity.BookmarkedBookEntity
import com.woody.model.BookModel
import com.woody.network.vo.BookResponse

fun BookModel.toBookmarkBookEntity(): BookmarkedBookEntity {
    return BookmarkedBookEntity(
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

fun BookModel.toBookEntity(): BookEntity {
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

fun BookEntity.toModel(): BookModel {
    return BookModel(
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

fun BookmarkedBookEntity.toModel(): BookModel {
    return BookModel(
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

fun BookResponse.toModel(): BookModel {
    return BookModel(
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