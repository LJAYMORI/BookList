package com.woody.data.repository

import com.woody.data.entity.SearchResultEntity
import io.reactivex.Single

interface BookListRepository {
    fun getBookList(query: String, display: Int, start: Int) : Single<Result<SearchResultEntity>>
}