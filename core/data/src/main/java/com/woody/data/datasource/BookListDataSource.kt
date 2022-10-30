package com.woody.data.datasource

import com.woody.data.entity.SearchResultEntity
import io.reactivex.Single

interface BookListDataSource {
    fun getBookList(query: String, display: Int, start: Int) : Single<SearchResultEntity>
}