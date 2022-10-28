package com.woody.data.repository

import com.woody.data.datasource.BookListDataSource
import com.woody.data.entity.SearchResultEntity
import com.woody.data.repository.BookListRepository
import com.woody.network.vo.SearchResultVO
import com.woody.util.SchedulerProvider
import io.reactivex.Single

class BookListRepositoryImpl constructor(
    private val remote: BookListDataSource
) : BookListRepository {
    override fun getBookList(
        query: String,
        display: Int,
        start: Int
    ): Single<Result<SearchResultEntity>> {
        return remote.getBookList(query = query, display = display, start = start)
            .map {
                Result.success(it)
            }
            .doOnError {
                Result.failure<Exception>(it)
            }
    }
}