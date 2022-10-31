package com.woody.data.repository

import com.woody.data.datasource.BookmarkListDataSource
import com.woody.data.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BookmarkBookRepository {
    fun getBookmarkList(): Flowable<Result<List<BookEntity>>>
    fun getBookmarkedBook(isbn: String): Single<Result<BookEntity>>
    fun insert(entity: BookEntity): Completable
    fun delete(isbn: String): Completable
}

class BookmarkBookRepositoryImpl(
    private val local: BookmarkListDataSource
) : BookmarkBookRepository {
    override fun getBookmarkList(): Flowable<Result<List<BookEntity>>> {
        return local.getBookmarkList()
            .map {  list ->
                Result.success(list)
            }
    }

    override fun getBookmarkedBook(isbn: String): Single<Result<BookEntity>> {
        return local.getBookmarkedBook(isbn)
            .map { Result.success(it) }
    }

    override fun insert(entity: BookEntity): Completable {
        return local.insert(entity)
    }

    override fun delete(isbn: String): Completable {
        return local.delete(isbn)
    }

}