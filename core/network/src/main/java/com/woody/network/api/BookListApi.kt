package com.woody.network.api

import com.woody.network.vo.SearchResultResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface BookListApi {
    @GET("v1/search/book.json")
    fun getBookList(@Query("query") query: String,
                    @Query("display") display: Int,
                    @Query("start") start: Int,
    ): Single<SearchResultResponse>
}