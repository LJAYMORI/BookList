package com.woody.domain.usecase

import com.woody.data.repository.BookmarkBookRepository
import com.woody.domain.scheduler.SchedulerProvider
import io.reactivex.Single

class IsBookmarkedUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: BookmarkBookRepository
) {
    operator fun invoke(isbn: String): Single<Boolean> {
        return repository.getBookmarkedBook(isbn)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .map { result ->
                result.getOrNull() != null
            }
    }
}