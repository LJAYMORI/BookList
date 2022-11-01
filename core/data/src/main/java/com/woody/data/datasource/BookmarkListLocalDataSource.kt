package com.woody.data.datasource

import com.woody.data.mapper.toBookmarkBookEntity
import com.woody.data.mapper.toModel
import com.woody.database.dao.BookmarkBookDao
import com.woody.model.BookModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class BookmarkListLocalDataSource(
    private val dao: BookmarkBookDao
) : BookmarkListDataSource {
    override fun getBookmarkList(): Flowable<List<BookModel>> {
        return dao.getBookmarkList()
            .map { list ->
                list.map { bookmarkedBook ->
                    bookmarkedBook.toModel()
                }
            }
    }

    override fun getBookmarkedBook(isbn: String): Single<BookModel> {
        return dao.get(isbn).map { it.first().toModel() }
    }

    override fun insert(entity: BookModel): Completable {
        return dao.insert(entity.toBookmarkBookEntity())
    }

    override fun delete(isbn: String): Completable {
        return dao.delete(isbn)
    }
}