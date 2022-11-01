package com.woody.booklist

import android.app.Application
import com.woody.bookmark.di.bookmarkModule
import com.woody.detail.di.bookDetailModule
import com.woody.search.di.bookListSearchModule
import com.woody.ui.image.ImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
            startKoin {
                androidContext(this@BookListApplication)
                androidLogger()
                modules(bookListSearchModule, bookmarkModule, bookDetailModule)
            }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ImageLoader.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        ImageLoader.onTrimMemory(this, level)
    }
}