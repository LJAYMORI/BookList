package com.woody.search.ui.data

import android.os.Parcelable
import com.woody.data.entity.BookEntity
import com.woody.data.entity.SearchResultEntity
import com.woody.network.vo.BookVO
import io.reactivex.Single
import kotlinx.parcelize.Parcelize

enum class BookListViewType {
    SEARCH, ITEM, EMPTY, UNKNOWN
}

@Parcelize
sealed class BookListData(val viewType: BookListViewType) : Parcelable {
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
        val emptyMessage: String = ""
    ) : BookListData(BookListViewType.EMPTY)

    data class Unknown(
        val unknownMessage: String
    ) : BookListData(BookListViewType.UNKNOWN)
}

fun Single<Result<SearchResultEntity>>.transformToBookListData(): Single<List<BookListData>> {
    return this.map { result ->
        result.getOrNull()?.let { entity ->
            entity.items.map { bookEntity ->
                BookListData.Item(
                    title = bookEntity.title,
                    author = bookEntity.author,
                    isbn = bookEntity.isbn,
                    price = bookEntity.price,
                    image = bookEntity.image,
                    publisher = bookEntity.publisher,
                    pubdate = bookEntity.pubdate,
                    discount = bookEntity.discount,
                    description = bookEntity.description
                )
            }
        } ?: arrayListOf(BookListData.Empty())
    }
}