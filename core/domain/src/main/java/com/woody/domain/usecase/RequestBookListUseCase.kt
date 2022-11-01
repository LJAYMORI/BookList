package com.woody.domain.usecase

import com.woody.data.repository.BookListRepository
import com.woody.data.repository.QueryPaginationParamRepository
import com.woody.domain.scheduler.SchedulerProvider
import com.woody.domain.usecase.base.SingleUseCase
import com.woody.model.QueryParamModel
import com.woody.model.SearchResultModel
import io.reactivex.Single

class RequestBookListUseCase(
    schedulerProvider: SchedulerProvider,
    private val bookListRepository: BookListRepository,
    private val queryParamRepository: QueryPaginationParamRepository,
) : SingleUseCase<QueryParamModel, SearchResultModel>(schedulerProvider) {

    operator fun invoke(): Single<Result<SearchResultModel>> {
        return invoke(param = queryParamRepository.getParam())
    }

    operator fun invoke(query: String): Single<Result<SearchResultModel>> {
        return invoke(param = queryParamRepository.getOrGenerateParam(query))
    }

    override fun buildStream(param: QueryParamModel): Single<Result<SearchResultModel>> {
        return bookListRepository.requestBookList(param)
            .doOnSuccess { result ->
                if (result.isSuccess) {
                    queryParamRepository.increasePageNumber()
                }
            }
    }
}