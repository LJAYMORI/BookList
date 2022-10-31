package com.woody.ui.recyclerview.viewholder.data

import com.woody.ui.recyclerview.adapter.BookListAdapter

data class BookListViewHolderData(
    val isbn: String,
    val title: String,
    val image: String,
    val author: String,
    val description: String,
    val isBookmarked: Boolean,
) : ViewHolderData<BookListAdapter.ViewType> {
    override val viewType: BookListAdapter.ViewType
        get() = BookListAdapter.ViewType.BOOK
}