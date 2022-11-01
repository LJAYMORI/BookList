package com.woody.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woody.database.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table")
    fun getBookList(): Flowable<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entityList: List<BookEntity>): Completable

    @Query("DELETE FROM book_table")
    fun deleteAll(): Completable
}