package com.woody.data.repository

import com.woody.model.QueryParamModel

interface QueryPaginationParamRepository {
    fun getOrGenerateParam(query: String): QueryParamModel
    fun getParam(): QueryParamModel
    fun increasePageNumber()
}

class QueryPaginationParamRepositoryImpl(
    private var param: QueryParamModel
) : QueryPaginationParamRepository {

    override fun getOrGenerateParam(query: String): QueryParamModel {
        return if (param.query == query) {
            param
        } else {
            param.copy(query = query, pageNumber = 1).also { newParam ->
                param = newParam
            }
        }
    }

    override fun getParam(): QueryParamModel {
        return param
    }

    override fun increasePageNumber() {
        param = param.copy(pageNumber = param.pageNumber + param.displayCount)
    }

}