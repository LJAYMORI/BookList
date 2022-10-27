package com.woody.domain_search.ui.data

import com.woody.module_data.entity.BookEntity
import com.woody.module_data.vo.BookVO

internal enum class BookListViewType {
    SEARCH, ITEM, EMPTY, UNKNOWN
}

internal sealed class BookListData(val viewType: BookListViewType) {
    data class Search(
        val defaultQuery: String
    ) : BookListData(BookListViewType.SEARCH)

    data class Item(
        val imageUrl: String,
        val title: String,
        val author: String,
        val description: String
    ) : BookListData(BookListViewType.ITEM)

    data class Empty(
        val emptyMessage: String
    ) : BookListData(BookListViewType.EMPTY)

    data class Unknown(
        val unknownMessage: String
    ) : BookListData(BookListViewType.UNKNOWN)
}

internal fun BookVO.toBookListData(): BookListData {
    return BookListData.Item(
        imageUrl = this.image ?: "",
        title = this.title ?: "",
        author = this.author ?: "",
        description = this.description ?: "",
    )
}

internal fun BookListData.Item.toBookEntity(): BookEntity {
    return BookEntity(
        title = this.title,
        author = this.author,
        isbn = "",
        price = "",
        image = this.imageUrl,
        publisher = "",
        pubdate = "",
        discount = "",
        description = this.description
    )
}