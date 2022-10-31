package com.woody.ui.mapper

import androidx.annotation.WorkerThread
import com.woody.data.entity.BookEntity
import com.woody.data.entity.SearchResultEntity
import com.woody.ui.mapper.BookListDataMapper.toBookListViewHolderData
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.Flowable
import io.reactivex.Single

object BookListDataMapper {
    fun BookEntity.toBookListViewHolderData(): BookListViewHolderData {
        return BookListViewHolderData(
            title = this.title,
            author = this.author,
            isbn = this.isbn,
            image = this.image,
            description = this.description,
            isBookmarked = false
        )
    }
}

@WorkerThread
fun Single<Result<SearchResultEntity>>.mapToBookListData(): Single<List<BookListViewHolderData>> {
    return this.map { result ->
        result.getOrNull()?.items?.let { items ->
            items.map { it.toBookListViewHolderData() }
        } ?: emptyList()
    }
}

@WorkerThread
fun Flowable<Result<List<BookEntity>>>.mapToBookListData(): Flowable<List<BookListViewHolderData>> {
    return this.map { result ->
        result.getOrNull()?.let { items ->
            items.map { it.toBookListViewHolderData() }
        } ?: emptyList()
    }
}
