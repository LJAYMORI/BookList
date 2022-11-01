package com.woody.data.repository

import com.woody.data.exception.NonFatalException
import com.woody.data.mapper.toBookEntity
import com.woody.data.mapper.toModel
import com.woody.database.dao.BookDao
import com.woody.model.BookModel
import com.woody.model.QueryParamModel
import com.woody.model.SearchResultModel
import com.woody.network.api.BookListApi
import io.reactivex.Flowable
import io.reactivex.Single

interface BookListRepository {
    fun getBookList(): Flowable<Result<List<BookModel>>>
    fun requestBookList(param: QueryParamModel): Single<Result<SearchResultModel>>
}

class BookListRepositoryImpl(
    private val api: BookListApi,
    private val dao: BookDao
) : BookListRepository {

    override fun getBookList(): Flowable<Result<List<BookModel>>> {
        return dao.getBookList().map { list ->
            list.map { entity -> entity.toModel() }
        }.map {
            Result.success(it)
        }
    }

    override fun requestBookList(param: QueryParamModel): Single<Result<SearchResultModel>> {
        return if (param.query.isEmpty()) {
            dao.deleteAll()
                .andThen(Single.just(Result.failure(NonFatalException.InvalidArgument)))
        } else {
            api.getBookList(
                query = param.query,
                display = param.displayCount,
                start = param.pageNumber
            ).flatMap { response ->
                val resultModel = response.toModel()
                dao.insertAll(resultModel.items.map { it.toBookEntity() })
                    .andThen(Single.just(Result.success(resultModel)))
            }
        }
    }
}