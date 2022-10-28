package com.woody.data.entity

import com.woody.network.vo.SearchResultVO


data class SearchResultEntity(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<BookEntity>
)

fun SearchResultVO.toEntity(): SearchResultEntity {
    return SearchResultEntity(
        lastBuildDate = lastBuildDate,
        total = total,
        start = start,
        display = display,
        items = items?.map { it.toEntity() } ?: emptyList()
    )
}