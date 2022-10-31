package com.woody.data.datasource

import com.woody.data.entity.BookEntity
import com.woody.data.entity.toBookmarkedBook
import com.woody.data.entity.toEntity
import com.woody.database.dao.BookmarkBookDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class BookmarkListLocalDataSource(
    private val dao: BookmarkBookDao
) : BookmarkListDataSource {
    override fun getBookmarkList(): Flowable<List<BookEntity>> {
        return dao.getBookmarkList()
            .map { list ->
                list.map { bookmarkedBook ->
                    bookmarkedBook.toEntity()
                }
            }
    }

    override fun getBookmarkedBook(isbn: String): Single<BookEntity> {
        return dao.get(isbn).map { it.first().toEntity() }
    }

    override fun insert(entity: BookEntity): Completable {
        return dao.insert(entity.toBookmarkedBook())
    }

    override fun delete(isbn: String): Completable {
        return dao.delete(isbn)
    }
}