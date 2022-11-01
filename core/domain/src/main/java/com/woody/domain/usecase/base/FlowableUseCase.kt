package com.woody.domain.usecase.base

import com.woody.domain.scheduler.SchedulerProvider
import io.reactivex.Flowable

abstract class FlowableUseCase<P, R>(
    private val schedulerProvider: SchedulerProvider
) {

    protected abstract fun buildStream(param: P): Flowable<Result<R>>

    operator fun invoke(param: P): Flowable<Result<R>> {
        return buildStream(param)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
    }
}