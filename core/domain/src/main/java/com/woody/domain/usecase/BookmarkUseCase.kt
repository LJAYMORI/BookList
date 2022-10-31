package com.woody.domain.usecase

import com.woody.data.entity.BookEntity
import com.woody.data.repository.BookmarkBookRepository
import com.woody.domain.scheduler.SchedulerProvider
import io.reactivex.Completable

class BookmarkUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: BookmarkBookRepository
) : SingleUseCase() {
    operator fun invoke(entity: BookEntity, flag: Boolean): Completable {
        val stream = if (flag) {
            repository.insert(entity)
        } else {
            repository.delete(entity.isbn)
        }
        return stream.subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .doOnSubscribe { disposable = it }
            .doOnComplete { disposable = null }
    }
}