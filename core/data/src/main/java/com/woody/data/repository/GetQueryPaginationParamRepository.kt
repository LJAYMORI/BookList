package com.woody.data.repository

import com.woody.data.entity.QueryParamEntity

interface QueryPaginationParamRepository {
    fun getOrGenerateParam(query: String): QueryParamEntity
    fun getParam(): QueryParamEntity
    fun increasePageNumber()
}

class QueryPaginationParamRepositoryImpl(
    private var param: QueryParamEntity
) : QueryPaginationParamRepository {

    override fun getOrGenerateParam(query: String): QueryParamEntity {
        return if (param.query == query) {
            param
        } else {
            param.copy(query = query, pageNumber = 1).also { newParam ->
                param = newParam
            }
        }
    }

    override fun getParam(): QueryParamEntity {
        return param
    }

    override fun increasePageNumber() {
        param = param.copy(pageNumber = param.pageNumber + param.displayCount)
    }

}