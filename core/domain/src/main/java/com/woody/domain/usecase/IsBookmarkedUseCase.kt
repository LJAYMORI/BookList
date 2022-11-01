package com.woody.domain.usecase

import com.woody.data.repository.BookmarkBookRepository
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class IsBookmarkedUseCase(
    schedulerProvider: SchedulerProvider,
    private val repository: BookmarkBookRepository
) : SingleUseCase<String, Boolean>(schedulerProvider) {

    override fun buildStream(param: String): Single<Result<Boolean>> {
        return repository.getBookmarkedBook(param)
            .map { result ->
                Result.success(result.getOrNull() != null)
            }
    }
}