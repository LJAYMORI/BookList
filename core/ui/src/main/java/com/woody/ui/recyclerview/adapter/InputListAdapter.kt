package com.woody.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woody.ui.databinding.ItemInputBinding
import com.woody.ui.recyclerview.viewholder.BaseViewHolder
import com.woody.ui.recyclerview.viewholder.EmptyViewHolder
import com.woody.ui.recyclerview.viewholder.InputViewHolder

class InputListAdapter(
    private val textChangedAction: (String) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    enum class ViewType {
        INPUT, UNKNOWN
    }

    interface InputListData {
        val viewType: ViewType
    }

    private val items = arrayListOf<InputListData>()

    fun init(query: String, hint: String) {
        items.clear()
        items.add(InputViewHolder.Data(defaultQuery = query, hint = hint))
        notifyItemRangeChanged(0, items.size)
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
                InputViewHolder(
                    binding = ItemInputBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    textChangedAction = textChangedAction
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
            holder is InputViewHolder && data is InputViewHolder.Data -> {
                holder.onBindViewHolder(data)
            }
        }
    }
}