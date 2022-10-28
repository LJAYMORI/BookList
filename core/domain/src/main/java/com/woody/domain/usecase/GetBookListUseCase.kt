package com.woody.domain.usecase

import com.woody.data.entity.SearchResultEntity
import com.woody.data.repository.BookListRepository
import com.woody.util.SchedulerProvider
import io.reactivex.Single

class GetBookListUseCase constructor(
    private val schedulerProvider: SchedulerProvider,
    private val repository: BookListRepository
) {
    operator fun invoke(query: String): Single<Result<SearchResultEntity>> {
        return repository.getBookList(query, 10, 1)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
    }
}