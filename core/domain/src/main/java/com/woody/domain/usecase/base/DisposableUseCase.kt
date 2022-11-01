package com.woody.domain.usecase.base

import io.reactivex.disposables.Disposable

open class DisposableUseCase {
    protected var disposable: Disposable? = null

    fun isDisposed(): Boolean {
        return disposable?.isDisposed ?: true
    }
}