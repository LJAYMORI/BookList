package com.woody.domain.usecase

import com.woody.data.entity.QueryParamEntity
import com.woody.data.entity.SearchResultEntity
import com.woody.data.repository.BookListRepository
import com.woody.data.repository.QueryPaginationParamRepository
import com.woody.util.SchedulerProvider
import io.reactivex.Single

class GetBookListUseCase constructor(
    private val schedulerProvider: SchedulerProvider,
    private val bookListRepository: BookListRepository,
    private val queryParamRepository: QueryPaginationParamRepository
) : SingleUseCase() {

    operator fun invoke(): Single<Result<SearchResultEntity>> {
        return getBookList(param = queryParamRepository.getParam())
    }

    operator fun invoke(query: String): Single<Result<SearchResultEntity>> {
        return getBookList(param = queryParamRepository.getOrGenerateParam(query))
    }

    private fun getBookList(param: QueryParamEntity): Single<Result<SearchResultEntity>> {
        return bookListRepository.getBookList(param)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .doOnSuccess { queryParamRepository.increasePageNumber() }
            .doOnSubscribe {
                disposable = it
            }
            .doOnEvent { _, _ ->
                disposable = null
            }
    }
}