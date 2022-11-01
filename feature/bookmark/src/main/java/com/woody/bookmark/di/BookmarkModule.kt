package com.woody.bookmark.di

import com.woody.bookmark.ui.BookmarkViewModel
import com.woody.data.repository.BookmarkBookRepository
import com.woody.data.repository.BookmarkBookRepositoryImpl
import com.woody.database.BookmarkBookDatabase
import com.woody.domain.scheduler.DefaultSchedulerProvider
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.BookmarkUseCase
import com.woody.domain.usecase.GetBookmarkedBookListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {

    single<SchedulerProvider> { DefaultSchedulerProvider() }

    single { BookmarkBookDatabase.getDatabase(androidContext()).bookmarkBookDao() }

    single<BookmarkBookRepository> { BookmarkBookRepositoryImpl(dao = get()) }

    factory { GetBookmarkedBookListUseCase(schedulerProvider = get(), repository = get()) }

    factory { BookmarkUseCase(schedulerProvider = get(), repository = get()) }

    viewModel { BookmarkViewModel(getBookmarkedBookListUseCase = get()) }
}