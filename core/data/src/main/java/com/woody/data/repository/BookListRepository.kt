package com.woody.data.repository

import com.woody.data.datasource.BookListDataSource
import com.woody.data.entity.QueryParamEntity
import com.woody.data.entity.SearchResultEntity
import io.reactivex.Single

interface BookListRepository {
    fun getBookList(param: QueryParamEntity): Single<Result<SearchResultEntity>>
}

class BookListRepositoryImpl constructor(
    private val remote: BookListDataSource
) : BookListRepository {

    override fun getBookList(
        param: QueryParamEntity
    ): Single<Result<SearchResultEntity>> {
        return remote.getBookList(
            query = param.query,
            display = param.displayCount,
            start = param.pageNumber
        ).map {
            Result.success(it)
        }
    }
}