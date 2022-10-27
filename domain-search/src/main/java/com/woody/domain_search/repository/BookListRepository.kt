package com.woody.domain_search.repository

import com.woody.domain_search.vo.SearchResultVO
import io.reactivex.Single

interface BookListRepository {
    fun getBookList(query: String, display: Int, start: Int) : Single<SearchResultVO>
}