package com.woody.model


data class SearchResultModel(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<BookModel>
)