package com.woody.domain_search.repository

import com.woody.module_data.vo.SearchResultVO
import io.reactivex.Single

interface BookListRepository {
    fun getBookList(query: String, display: Int, start: Int) : Single<SearchResultVO>
}