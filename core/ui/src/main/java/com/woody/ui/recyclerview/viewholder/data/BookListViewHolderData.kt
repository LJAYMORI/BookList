package com.woody.ui.recyclerview.viewholder.data

import android.os.Parcelable
import com.woody.ui.recyclerview.adapter.BookListAdapter
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookListViewHolderData(
    val title: String,
    val author: String,
    val isbn: String,
    val price: String,
    val image: String,
    val publisher: String,
    val pubdate: String,
    val discount: String,
    val description: String,
) : ViewHolderData<BookListAdapter.ViewType>, Parcelable {
    override val viewType: BookListAdapter.ViewType
        get() = BookListAdapter.ViewType.BOOK
}