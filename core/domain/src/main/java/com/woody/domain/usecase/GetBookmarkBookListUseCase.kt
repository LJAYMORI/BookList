package com.woody.domain.usecase

import com.woody.data.entity.BookEntity
import com.woody.data.repository.BookmarkBookRepository
import com.woody.domain.scheduler.SchedulerProvider
import io.reactivex.Flowable

class GetBookmarkBookListUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: BookmarkBookRepository
) : SingleUseCase() {

    operator fun invoke(): Flowable<Result<List<BookEntity>>> {
        return repository.getBookmarkList()
            .observeOn(schedulerProvider.ui)
    }
}