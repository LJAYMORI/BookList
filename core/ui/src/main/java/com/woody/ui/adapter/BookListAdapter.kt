package com.woody.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woody.ui.databinding.ItemBookBinding
import com.woody.ui.databinding.ItemMessageBinding
import com.woody.ui.viewholder.BaseViewHolder
import com.woody.ui.viewholder.BookListItemViewHolder
import com.woody.ui.viewholder.EmptyViewHolder
import com.woody.ui.viewholder.BookListMessageViewHolder

class BookListAdapter(
    private val itemClickAction: (BookListItemViewHolder.Data) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    enum class ViewType {
        BOOK, EMPTY, UNKNOWN
    }

    interface BookListData {
        val viewType: ViewType
    }

    private val items = arrayListOf<BookListData>()

    fun setItems(list: List<BookListData>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun addItems(list: List<BookListData>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.ordinal
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (ViewType.values()[viewType]) {
            ViewType.BOOK -> {
                BookListItemViewHolder(
                    binding = ItemBookBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    itemClickAction = itemClickAction
                )
            }
            ViewType.EMPTY -> {
                BookListMessageViewHolder(
                    binding = ItemMessageBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            ViewType.UNKNOWN -> {
                EmptyViewHolder(parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val data = items.getOrNull(position) ?: return
        when {
            holder is BookListItemViewHolder && data is BookListItemViewHolder.Data -> {
                holder.onBindViewHolder(data)
            }
            holder is BookListMessageViewHolder && data is BookListMessageViewHolder.MessageData -> {
                holder.onBindViewHolder(data)
            }
        }
    }
}