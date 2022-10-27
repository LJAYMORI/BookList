package com.woody.domain_search.datasource

import com.woody.domain_search.vo.SearchResultVO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface BookListApi {
    @GET("v1/search/book.json")
    fun getBookList(@Query("query") query: String,
                    @Query("display") display: Int,
                    @Query("start") start: Int,
    ): Single<SearchResultVO>
}