package com.woody.data.datasource

import com.woody.model.BookModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BookmarkListDataSource {
    fun getBookmarkList(): Flowable<List<BookModel>>
    fun getBookmarkedBook(isbn: String): Single<BookModel>
    fun insert(entity: BookModel): Completable
    fun delete(isbn: String): Completable
}