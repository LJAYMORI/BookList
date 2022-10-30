package com.woody.network.vo

data class SearchResultVO(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<BookVO>?
)