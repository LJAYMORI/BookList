package com.woody.data.repository

import com.woody.model.BookModel
import com.woody.data.exception.NonFatalException
import com.woody.data.mapper.toBookmarkBookEntity
import com.woody.data.mapper.toModel
import com.woody.database.dao.BookmarkBookDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BookmarkBookRepository {
    fun getBookmarkList(): Flowable<Result<List<BookModel>>>
    fun getBookmarkedBook(isbn: String): Single<Result<BookModel>>
    fun insert(model: BookModel): Completable
    fun delete(isbn: String): Completable
}

class BookmarkBookRepositoryImpl(
    private val dao: BookmarkBookDao
) : BookmarkBookRepository {
    override fun getBookmarkList(): Flowable<Result<List<BookModel>>> {
        return dao.getBookmarkList()
            .map {  list ->
                list.map { it.toModel() }.let { modelList ->
                    if (modelList.isNotEmpty()) {
                        Result.success(modelList)
                    } else {
                        Result.failure(NonFatalException.NoResult)
                    }
                }
            }
    }

    override fun getBookmarkedBook(isbn: String): Single<Result<BookModel>> {
        return dao.get(isbn)
            .map { list ->
                list.firstOrNull()?.toModel()?.let { model ->
                    Result.success(model)
                } ?: Result.failure(NonFatalException.NoResult)
            }
    }

    override fun insert(model: BookModel): Completable {
        return dao.insert(model.toBookmarkBookEntity())
    }

    override fun delete(isbn: String): Completable {
        return dao.delete(isbn)
    }
}