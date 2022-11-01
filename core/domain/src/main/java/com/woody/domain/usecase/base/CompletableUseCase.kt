package com.woody.domain.usecase.base

import com.woody.domain.scheduler.SchedulerProvider
import io.reactivex.Completable

abstract class CompletableUseCase<P>(
    private val schedulerProvider: SchedulerProvider
) : DisposableUseCase() {

    protected abstract fun buildStream(param: P): Completable

    operator fun invoke(param: P): Completable {
        return buildStream(param)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .doOnSubscribe { disposable = it }
            .doOnEvent { disposable = null }
    }
}