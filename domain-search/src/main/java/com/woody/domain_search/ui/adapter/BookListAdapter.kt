package com.woody.domain_search.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woody.domain_search.R
import com.woody.domain_search.databinding.ItemBookListBinding
import com.woody.domain_search.ui.data.BookListData
import com.woody.domain_search.ui.data.BookListViewType
import com.woody.domain_search.ui.viewholder.BookListViewHolder

internal class BookListAdapter(
    private val onItemClickAction: (BookListData.Item) -> Unit,
    private val onEmptyClickAction: (BookListData.Empty) -> Unit
) : RecyclerView.Adapter<BookListViewHolder>() {

    private val list = arrayListOf<BookListData>()

    fun setItems(items: List<BookListData>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType.ordinal
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (BookListViewType.values()[viewType]) {
            BookListViewType.SEARCH -> {
                BookListViewHolder.Search(View(parent.context))
            }
            BookListViewType.ITEM -> {
                BookListViewHolder.Item(
                    binding = ItemBookListBinding.inflate(inflater, parent, false),
                    onItemClickAction = onItemClickAction
                )
            }
            BookListViewType.EMPTY -> {
                BookListViewHolder.Empty(View(parent.context))
            }
            BookListViewType.UNKNOWN -> {
                BookListViewHolder.Unknown(View(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.onBindView(list[position])
    }
}