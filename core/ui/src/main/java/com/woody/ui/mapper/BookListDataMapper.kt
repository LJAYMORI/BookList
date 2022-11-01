package com.woody.ui.mapper

import androidx.annotation.WorkerThread
import com.woody.model.BookModel
import com.woody.model.SearchResultModel
import com.woody.ui.mapper.BookListDataMapper.toBookListViewHolderData
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.Flowable
import io.reactivex.Single

object BookListDataMapper {
    fun BookModel.toBookListViewHolderData(bookmarked: Boolean = false): BookListViewHolderData {
        return BookListViewHolderData(
            title = this.title,
            author = this.author,
            isbn = this.isbn,
            price = this.price,
            image = this.image,
            publisher = this.publisher,
            pubdate = this.pubdate,
            discount = this.discount,
            description = this.description,
            isBookmarked = bookmarked
        )
    }

    fun BookListViewHolderData.toModel(): BookModel {
        return BookModel(
            title = this.title,
            author = this.author,
            isbn = this.isbn,
            price = this.price,
            image = this.image,
            publisher = this.publisher,
            pubdate = this.pubdate,
            discount = this.discount,
            description = this.description,
        )
    }
}

@WorkerThread
fun Single<Pair<Result<SearchResultModel>, Result<List<BookModel>>>>.mapToBookListData(): Single<List<BookListViewHolderData>> {
    return this.map { (searchResult, bookmarkListResult) ->
        val bookmarkedList = bookmarkListResult.getOrNull() ?: emptyList()
        searchResult.getOrNull()?.items?.let { items ->
            items.map { bookModel ->
                bookModel.toBookListViewHolderData(
                    bookmarked = bookmarkedList.find { it.isbn == bookModel.isbn } != null
                )
            }
        } ?: emptyList()
    }
}

@WorkerThread
fun Flowable<Result<List<BookModel>>>.mapToBookListData(): Flowable<List<BookListViewHolderData>> {
    return this.map { result ->
        result.getOrNull()?.let { items ->
            items.map { it.toBookListViewHolderData(bookmarked = true) }
        } ?: emptyList()
    }
}
