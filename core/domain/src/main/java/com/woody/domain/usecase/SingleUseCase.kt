package com.woody.domain.usecase

import io.reactivex.disposables.Disposable

abstract class SingleUseCase {
    protected var disposable: Disposable? = null

    fun isDisposed(): Boolean {
        return disposable?.isDisposed ?: true
    }
}