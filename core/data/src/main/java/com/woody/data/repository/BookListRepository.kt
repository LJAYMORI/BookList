package com.woody.data.repository

import com.woody.data.exception.NonFatalException
import com.woody.data.mapper.toModel
import com.woody.model.QueryParamModel
import com.woody.model.SearchResultModel
import com.woody.network.api.BookListApi
import io.reactivex.Single

interface BookListRepository {
    fun requestBookList(param: QueryParamModel): Single<Result<SearchResultModel>>
}

class BookListRepositoryImpl(
    private val api: BookListApi
) : BookListRepository {

    override fun requestBookList(param: QueryParamModel): Single<Result<SearchResultModel>> {
        return if (param.query.isEmpty()) {
            Single.just(Result.failure(NonFatalException.InvalidArgument))
        } else {
            api.getBookList(
                query = param.query,
                display = param.displayCount,
                start = param.pageNumber
            ).map { response ->
                Result.success(response.toModel())
            }
        }
    }
}