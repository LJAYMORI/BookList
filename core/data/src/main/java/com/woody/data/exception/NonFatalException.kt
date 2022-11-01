package com.woody.data.exception

sealed class NonFatalException : Throwable() {
    object InvalidArgument : NonFatalException()
    object NoResult : NonFatalException()
}