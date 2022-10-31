package com.woody.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woody.database.entity.BookmarkedBook
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface BookmarkBookDao {

    @Query("SELECT * FROM bookmark_book_table")
    fun getBookmarkList(): Flowable<List<BookmarkedBook>>

    @Query("SELECT * FROM bookmark_book_table WHERE isbn = :isbn")
    fun get(isbn: String): Single<List<BookmarkedBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmarkedBook: BookmarkedBook): Completable

    @Query("DELETE FROM bookmark_book_table where isbn = :isbn")
    fun delete(isbn: String): Completable
}