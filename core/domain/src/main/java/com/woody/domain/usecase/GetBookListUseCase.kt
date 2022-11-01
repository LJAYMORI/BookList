package com.woody.domain.usecase

import com.woody.data.repository.BookListRepository
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.base.FlowableUseCase
import com.woody.model.BookModel
import io.reactivex.Flowable

class GetBookListUseCase(
    schedulerProvider: SchedulerProvider,
    private val repository: BookListRepository
) : FlowableUseCase<Unit, List<BookModel>>(schedulerProvider) {
    override fun buildStream(param: Unit): Flowable<Result<List<BookModel>>> {
        return repository.getBookList()
    }
}