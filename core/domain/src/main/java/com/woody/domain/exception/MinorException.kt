package com.woody.domain.exception

sealed interface MinorException {
    object NotFinishedRequest : MinorException
}