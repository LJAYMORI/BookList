package com.woody.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woody.ui.databinding.ItemBookBinding
import com.woody.ui.recyclerview.diffutil.DefaultViewDataDiffUtil
import com.woody.ui.recyclerview.viewholder.BaseViewHolder
import com.woody.ui.recyclerview.viewholder.BookListItemViewHolder
import com.woody.ui.recyclerview.viewholder.FooterEndViewHolder
import com.woody.ui.recyclerview.viewholder.data.ViewHolderData
import com.woody.ui.recyclerview.viewholder.ViewHolderViewType
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import com.woody.ui.recyclerview.viewholder.data.UnknownViewHolder

class BookListAdapter(
    private val itemClickAction: (BookListViewHolderData) -> Unit,
    private val bookmarkClickAction: (BookListViewHolderData) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    enum class ViewType : ViewHolderViewType {
        BOOK, UNKNOWN
    }

    private val items = arrayListOf<ViewHolderData<ViewType>>()

    fun getBookListDataList(): List<BookListViewHolderData> {
        return items.mapNotNull { it as? BookListViewHolderData }
    }

    fun addItems(list: List<ViewHolderData<ViewType>>) {
        setItems(items + list)
    }

    fun setItems(list: List<ViewHolderData<ViewType>>) {
        DiffUtil.calculateDiff(DefaultViewDataDiffUtil(oldList = items, newList = list)).apply {
            items.clear()
            items.addAll(list)
            dispatchUpdatesTo(this@BookListAdapter)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.viewType?.ordinal ?: ViewType.UNKNOWN.ordinal
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
                    itemClickAction = itemClickAction,
                    bookmarkClickAction = bookmarkClickAction
                )
            }
            ViewType.UNKNOWN -> {
                UnknownViewHolder(parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val data = items.getOrNull(position) ?: return
        when {
            holder is BookListItemViewHolder && data is BookListViewHolderData -> {
                holder.onBindViewHolder(data)
            }
        }
    }
}