package com.woody.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.woody.database.dao.BookDao
import com.woody.database.dao.BookmarkBookDao
import com.woody.database.entity.BookEntity
import com.woody.database.entity.BookmarkedBookEntity

@Database(
    entities = [
        BookEntity::class,
        BookmarkedBookEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BookmarkBookDatabase : RoomDatabase() {
    abstract fun bookmarkBookDao(): BookmarkBookDao
    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: BookmarkBookDatabase? = null

        fun getDatabase(context: Context): BookmarkBookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    BookmarkBookDatabase::class.java
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}