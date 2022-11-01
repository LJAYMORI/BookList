package com.woody.ui.recyclerview.viewholder

import com.woody.ui.databinding.ItemBookBinding
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData

class BookListItemViewHolder(
    private val binding: ItemBookBinding,
    itemClickAction: (BookListViewHolderData) -> Unit,
    bookmarkClickAction: (BookListViewHolderData) -> Unit
) : BaseViewHolder<BookListViewHolderData>(binding.root) {

    private var data: BookListViewHolderData? = null

    init {
        binding.bookListItem.setOnClickListener {
            data?.let(itemClickAction)
        }
        binding.bookListItem.setOnBookmarkClickAction {
            data?.let(bookmarkClickAction)
        }
    }

    override fun onBindViewHolder(data: BookListViewHolderData) {
        this.data = data
        with (binding.bookListItem) {
            updateImage(data.image)
            updateTitle(data.title)
            updateAuthor(data.author)
            updateDescription(data.description)
            updateBookmark(data.isBookmarked)
        }
    }
}