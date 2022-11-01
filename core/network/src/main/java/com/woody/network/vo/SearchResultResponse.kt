package com.woody.network.vo

data class SearchResultResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<BookResponse>?
)