package com.woody.search.repository

import com.woody.search.datasource.BookListDataSource
import com.woody.module_data.vo.SearchResultVO
import io.reactivex.Single

class BookListRepositoryImpl(
    private val remote: BookListDataSource
) : BookListRepository {
    override fun getBookList(query: String, display: Int, start: Int): Single<SearchResultVO> {
        return remote.getBookList(
            query = query,
            display = display,
            start = start
        )
    }
}