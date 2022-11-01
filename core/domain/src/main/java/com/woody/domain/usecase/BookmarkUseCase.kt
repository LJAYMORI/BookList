package com.woody.domain.usecase

import com.woody.data.repository.BookmarkBookRepository
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.base.CompletableUseCase
import com.woody.model.BookModel
import io.reactivex.Completable

class BookmarkUseCase(
    schedulerProvider: SchedulerProvider,
    private val repository: BookmarkBookRepository,
) : CompletableUseCase<BookmarkUseCase.Param>(schedulerProvider) {

    override fun buildStream(param: Param): Completable {
        return if (param.flag) {
            repository.insert(param.model)
        } else {
            repository.delete(param.model.isbn)
        }
    }

    operator fun invoke(model: BookModel, flag: Boolean) = invoke(Param(model, flag))

    data class Param(val model: BookModel, val flag: Boolean)
}