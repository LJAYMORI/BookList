package com.woody.domain_search.datasource

import com.woody.domain_search.vo.SearchResultVO
import io.reactivex.Single

class BookListRemoteDataSource(
    private val api: BookListApi
) : BookListDataSource {
    override fun getBookList(query: String, display: Int, start: Int): Single<SearchResultVO> {
        return api.getBookList(
            query = query,
            display = display,
            start = start
        )
    }
}