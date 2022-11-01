package com.woody.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woody.ui.databinding.ItemBookNameInputBinding
import com.woody.ui.recyclerview.diffutil.DefaultViewDataDiffUtil
import com.woody.ui.recyclerview.viewholder.BaseViewHolder
import com.woody.ui.recyclerview.viewholder.BookNameInputViewHolder
import com.woody.ui.recyclerview.viewholder.ViewHolderViewType
import com.woody.ui.recyclerview.viewholder.data.BookNameInputViewHolderData
import com.woody.ui.recyclerview.viewholder.data.UnknownViewHolder
import com.woody.ui.recyclerview.viewholder.data.ViewHolderData

class InputListAdapter(
    private val textChangedAction: (String) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    enum class ViewType : ViewHolderViewType {
        INPUT, UNKNOWN
    }

    private val items = arrayListOf<ViewHolderData<ViewType>>()
    var currentQuery: String = ""
        private set

    fun init(query: String, hint: String) {
        val newList = arrayListOf(BookNameInputViewHolderData(defaultQuery = query, hint = hint))
        DiffUtil.calculateDiff(
            DefaultViewDataDiffUtil(
                oldList = items,
                newList = newList
            )
        ).apply {
            items.clear()
            items.addAll(newList)
            dispatchUpdatesTo(this@InputListAdapter)
        }
    }

    fun getBookNameInputDataList(): List<BookNameInputViewHolderData> {
        return items.mapNotNull { it as? BookNameInputViewHolderData }
    }

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.viewType?.ordinal ?: ViewType.UNKNOWN.ordinal
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (ViewType.values()[viewType]) {
            ViewType.INPUT -> {
                BookNameInputViewHolder(
                    binding = ItemBookNameInputBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    textChangedAction = { changedText ->
                        currentQuery = changedText
                        textChangedAction.invoke(changedText)
                    }
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
            holder is BookNameInputViewHolder && data is BookNameInputViewHolderData -> {
                holder.onBindViewHolder(data)
            }
        }
    }
}