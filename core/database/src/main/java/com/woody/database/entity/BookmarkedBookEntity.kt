package com.woody.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_book_table")
class BookmarkedBookEntity(
    @PrimaryKey
    val isbn: String,
    val title: String,
    val author: String,
    val price: String,
    val image: String,
    val publisher: String,
    val pubdate: String,
    val discount: String,
    val description: String,
)