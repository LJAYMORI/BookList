package com.woody.bookmark.di

import com.woody.bookmark.ui.BookmarkViewModel
import com.woody.data.datasource.BookmarkListDataSource
import com.woody.data.datasource.BookmarkListLocalDataSource
import com.woody.data.repository.BookmarkBookRepository
import com.woody.data.repository.BookmarkBookRepositoryImpl
import com.woody.database.BookmarkBookDatabase
import com.woody.domain.usecase.BookmarkUseCase
import com.woody.domain.usecase.GetBookmarkBookListUseCase
import com.woody.domain.scheduler.DefaultSchedulerProvider
import com.woody.domain.scheduler.SchedulerProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {

    single<SchedulerProvider> { DefaultSchedulerProvider() }

    single { BookmarkBookDatabase.getDatabase(androidContext()).bookmarkBookDao() }

    single<BookmarkListDataSource> { BookmarkListLocalDataSource(dao = get()) }

    factory<BookmarkBookRepository> { BookmarkBookRepositoryImpl(local = get()) }

    factory { GetBookmarkBookListUseCase(schedulerProvider = get(), repository = get()) }

    factory { BookmarkUseCase(schedulerProvider = get(), repository = get()) }

    viewModel { BookmarkViewModel(getBookmarkBookListUseCase = get()) }
}