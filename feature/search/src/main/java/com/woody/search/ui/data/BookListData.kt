package com.woody.search.ui.data

import android.os.Parcelable
import com.woody.module_data.entity.BookEntity
import com.woody.module_data.vo.BookVO
import kotlinx.parcelize.Parcelize

internal enum class BookListViewType {
    SEARCH, ITEM, EMPTY, UNKNOWN
}

@Parcelize
internal sealed class BookListData(val viewType: BookListViewType) : Parcelable {
    data class Search(
        val defaultQuery: String
    ) : BookListData(BookListViewType.SEARCH)

    data class Item(
        val title: String,
        val author: String,
        val isbn: String,
        val price: String,
        val image: String,
        val publisher: String,
        val pubdate: String,
        val discount: String,
        val description: String,
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
        title = this.title ?: "",
        author = this.author ?: "",
        isbn = this.isbn ?: "",
        price = this.price ?: "",
        image = this.image ?: "",
        publisher = this.publisher ?: "",
        pubdate = this.pubdate ?: "",
        discount = this.discount ?: "",
        description = this.description ?: ""
    )
}

internal fun BookListData.Item.toBookEntity(): BookEntity {
    return BookEntity(
        title = this.title,
        author = this.author,
        isbn = this.isbn,
        price = this.price,
        image = this.image,
        publisher = this.publisher,
        pubdate = this.pubdate,
        discount = this.discount,
        description = this.description
    )
}