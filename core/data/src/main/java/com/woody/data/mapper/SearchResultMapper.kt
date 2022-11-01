package com.woody.data.mapper

import com.woody.model.SearchResultModel
import com.woody.network.vo.SearchResultResponse

fun SearchResultResponse.toModel(): SearchResultModel {
    return SearchResultModel(
        lastBuildDate = lastBuildDate,
        total = total,
        start = start,
        display = display,
        items = items?.map { it.toModel() } ?: emptyList()
    )
}