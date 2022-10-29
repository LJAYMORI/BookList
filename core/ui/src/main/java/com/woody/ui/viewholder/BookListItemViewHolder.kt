package com.woody.ui.viewholder

import com.woody.ui.adapter.BookListAdapter
import com.woody.ui.databinding.ItemBookBinding

class BookListItemViewHolder(
    private val binding: ItemBookBinding,
    itemClickAction: (String) -> Unit
) : BaseViewHolder<BookListItemViewHolder.Data>(binding.root) {

    data class Data(
        override val viewType: BookListAdapter.ViewType = BookListAdapter.ViewType.BOOK,
        val isbn: String,
        val image: String,
        val title: String,
        val description: String,
        val author: String
    ):BookListAdapter.BookListData

    private var isbn: String? = null

    init {
        isbn?.let(itemClickAction)
    }

    override fun onBindViewHolder(data: Data) {
        isbn = data.isbn
        with (binding.bookListItem) {
            updateImage(data.image)
            updateTitle(data.title)
            updateAuthor(data.author)
            updateDescription(data.description)
        }
    }
}