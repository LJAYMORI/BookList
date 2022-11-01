package com.woody.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woody.database.entity.BookmarkedBookEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface BookmarkBookDao {

    @Query("SELECT * FROM bookmark_book_table")
    fun getBookmarkList(): Flowable<List<BookmarkedBookEntity>>

    @Query("SELECT * FROM bookmark_book_table WHERE isbn = :isbn")
    fun get(isbn: String): Single<List<BookmarkedBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: BookmarkedBookEntity): Completable

    @Query("DELETE FROM bookmark_book_table where isbn = :isbn")
    fun delete(isbn: String): Completable
}