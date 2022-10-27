package com.woody.domain_search.datasource

import com.woody.domain_search.vo.SearchResultVO
import io.reactivex.Single

interface BookListDataSource {
    fun getBookList(query: String, display: Int, start: Int) : Single<SearchResultVO>
}