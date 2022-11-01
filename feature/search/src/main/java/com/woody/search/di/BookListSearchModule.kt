package com.woody.search.di

import com.woody.data.repository.BookListRepository
import com.woody.data.repository.BookListRepositoryImpl
import com.woody.data.repository.QueryPaginationParamRepository
import com.woody.data.repository.QueryPaginationParamRepositoryImpl
import com.woody.domain.scheduler.DefaultSchedulerProvider
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.RequestBookListUseCase
import com.woody.model.QueryParamModel
import com.woody.network.api.BookListApi
import com.woody.network.retrofit.HeaderInterceptor
import com.woody.network.retrofit.RetrofitFactory
import com.woody.search.ui.BookListSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val bookListSearchModule = module {

    single<SchedulerProvider> { DefaultSchedulerProvider() }

    single { HeaderInterceptor(hashMapOf("X-Naver-Client-Id" to "KiXBNHcPVH9OxOqLAkio", "X-Naver-Client-Secret" to "l5iWGZAZmt")) }

    single {
        RetrofitFactory.create(
            baseUrl = "https://openapi.naver.com",
            get<HeaderInterceptor>()
        )
    }

    single { get<Retrofit>().create(BookListApi::class.java) }

    single<BookListRepository> { BookListRepositoryImpl(api = get()) }

    single { QueryParamModel(query = "", displayCount = 10, pageNumber = 1) }

    factory <QueryPaginationParamRepository> { QueryPaginationParamRepositoryImpl(param = get()) }

    factory {
        RequestBookListUseCase(
            schedulerProvider = get(),
            bookListRepository = get(),
            queryParamRepository = get()
        )
    }

    viewModel {
        BookListSearchViewModel(requestBookListUseCase = get())
    }
}