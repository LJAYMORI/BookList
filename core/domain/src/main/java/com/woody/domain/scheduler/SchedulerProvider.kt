package com.woody.domain.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    val io: Scheduler
    val ui: Scheduler
}

class DefaultSchedulerProvider : SchedulerProvider {
    override val io: Scheduler get() = Schedulers.io()
    override val ui: Scheduler get() = AndroidSchedulers.mainThread()
}