package com.woody.domain.usecase

import com.woody.data.repository.BookmarkBookRepository
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.base.FlowableUseCase
import com.woody.model.BookModel
import io.reactivex.Flowable

class GetBookmarkedBookListUseCase(
    schedulerProvider: SchedulerProvider,
    private val repository: BookmarkBookRepository
) : FlowableUseCase<Unit, List<BookModel>>(schedulerProvider) {

    override fun buildStream(param: Unit): Flowable<Result<List<BookModel>>> {
        return repository.getBookmarkList()
    }
}