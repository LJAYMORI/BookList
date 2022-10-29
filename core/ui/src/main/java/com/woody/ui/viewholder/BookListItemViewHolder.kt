package com.woody.ui.viewholder

import com.woody.ui.adapter.BookListAdapter
import com.woody.ui.databinding.ItemBookBinding

class BookListItemViewHolder(
    private val binding: ItemBookBinding,
    itemClickAction: (Data) -> Unit
) : BaseViewHolder<BookListItemViewHolder.Data>(binding.root) {

    data class Data(
        override val viewType: BookListAdapter.ViewType = BookListAdapter.ViewType.BOOK,
        val title: String,
        val author: String,
        val isbn: String,
        val price: String,
        val image: String,
        val publisher: String,
        val pubdate: String,
        val discount: String,
        val description: String,
    ):BookListAdapter.BookListData

    private var data: Data? = null

    init {
        itemView.setOnClickListener {
            data?.let(itemClickAction)
        }
    }

    override fun onBindViewHolder(data: Data) {
        this.data = data
        with (binding.bookListItem) {
            updateImage(data.image)
            updateTitle(data.title)
            updateAuthor(data.author)
            updateDescription(data.description)
        }
    }
}