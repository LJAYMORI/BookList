package com.woody.domain_search.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woody.domain_search.databinding.ItemBookListBinding
import com.woody.domain_search.ui.data.BookListData

internal abstract class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindView(data: BookListData)

    class Search(itemView: View) : BookListViewHolder(itemView) {
        override fun onBindView(data: BookListData) {
            // do nothing
        }
    }

    class Item(
        private val binding: ItemBookListBinding,
        onItemClickAction: (BookListData.Item) -> Unit
    ) : BookListViewHolder(binding.root) {

        private var data: BookListData.Item? = null

        init {
            itemView.setOnClickListener {
                data?.let(onItemClickAction)
            }
        }

        override fun onBindView(data: BookListData) {
            this.data = (data as? BookListData.Item) ?: return
            with(binding.bookListItem) {
                updateImage(data.imageUrl)
                updateTitle(data.title)
                updateAuthor(data.author)
                updateDescription(data.description)
            }
        }
    }

    class Empty(itemView: View) : BookListViewHolder(itemView) {
        override fun onBindView(data: BookListData) {
            // do nothing
        }
    }

    class Unknown(itemView: View) : BookListViewHolder(itemView) {
        override fun onBindView(data: BookListData) {
            // do nothing
        }
    }
}