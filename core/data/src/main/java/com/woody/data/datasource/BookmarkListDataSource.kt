package com.woody.data.datasource

import com.woody.data.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BookmarkListDataSource {
    fun getBookmarkList(): Flowable<List<BookEntity>>
    fun getBookmarkedBook(isbn: String): Single<BookEntity>
    fun insert(entity: BookEntity): Completable
    fun delete(isbn: String): Completable
}