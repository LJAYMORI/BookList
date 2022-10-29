package com.woody.ui.viewholder

import com.woody.ui.adapter.BookListAdapter
import com.woody.ui.databinding.ItemMessageBinding

class BookListMessageViewHolder(
    private val binding: ItemMessageBinding
) : BaseViewHolder<BookListMessageViewHolder.MessageData>(binding.root) {

    data class MessageData(
        override val viewType: BookListAdapter.ViewType = BookListAdapter.ViewType.EMPTY,
        val message: String
    ): BookListAdapter.BookListData

    override fun onBindViewHolder(data: MessageData) {
        binding.messageText.text = data.message
    }
}