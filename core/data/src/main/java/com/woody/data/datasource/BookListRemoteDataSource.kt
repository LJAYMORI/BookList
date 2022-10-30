package com.woody.data.datasource

import com.woody.data.entity.SearchResultEntity
import com.woody.data.entity.toEntity
import com.woody.network.api.BookListApi
import io.reactivex.Single

class BookListRemoteDataSource(
    private val api: BookListApi
) : BookListDataSource {
    override fun getBookList(query: String, display: Int, start: Int): Single<SearchResultEntity> {
        return api.getBookList(
            query = query,
            display = display,
            start = start
        ).map { it.toEntity() }
    }
}