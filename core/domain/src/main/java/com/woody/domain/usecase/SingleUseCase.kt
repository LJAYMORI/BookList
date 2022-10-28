package com.woody.domain.usecase

import com.woody.util.SchedulerProvider

class SingleUseCase<P, R>(
    private val schedulers: SchedulerProvider
) {

}