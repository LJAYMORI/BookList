package com.woody.search.di

import com.woody.data.datasource.BookListDataSource
import com.woody.data.datasource.BookListRemoteDataSource
import com.woody.data.repository.BookListRepository
import com.woody.data.repository.BookListRepositoryImpl
import com.woody.domain.usecase.GetBookListUseCase
import com.woody.network.api.BookListApi
import com.woody.network.retrofit.HeaderInterceptor
import com.woody.network.retrofit.RetrofitFactory
import com.woody.search.ui.BookListSearchViewModel
import com.woody.util.DefaultSchedulerProvider
import com.woody.util.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val bookListSearchModules = module {

    single<SchedulerProvider> { DefaultSchedulerProvider() }

    single { HeaderInterceptor(hashMapOf("X-Naver-Client-Id" to "KiXBNHcPVH9OxOqLAkio", "X-Naver-Client-Secret" to "l5iWGZAZmt")) }

    single {
        RetrofitFactory.create(
            baseUrl = "https://openapi.naver.com",
            get<HeaderInterceptor>()
        )
    }

    single { get<Retrofit>().create(BookListApi::class.java) }

    factory<BookListDataSource> { BookListRemoteDataSource(api = get()) }

    factory<BookListRepository> { BookListRepositoryImpl(remote = get()) }

    factory { GetBookListUseCase(schedulerProvider = get(), repository = get()) }

    viewModel { BookListSearchViewModel(getBookListUseCase = get()) }
}