package com.woody.domain.usecase.base

import com.woody.domain.scheduler.SchedulerProvider
import io.reactivex.Single


abstract class SingleUseCase<P, R>(
    private val schedulerProvider: SchedulerProvider
) : DisposableUseCase() {

    protected abstract fun buildStream(param: P): Single<Result<R>>

    operator fun invoke(param: P): Single<Result<R>> {
        return buildStream(param)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .doOnSubscribe { disposable = it }
            .doOnEvent { _, _ -> disposable = null }
    }
}