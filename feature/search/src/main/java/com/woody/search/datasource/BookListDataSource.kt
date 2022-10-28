package com.woody.search.datasource

import com.woody.module_data.vo.SearchResultVO
import io.reactivex.Single

interface BookListDataSource {
    fun getBookList(query: String, display: Int, start: Int) : Single<SearchResultVO>
}