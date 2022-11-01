package com.woody.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woody.ui.databinding.ItemFooterEndBinding
import com.woody.ui.databinding.ItemFooterLoadingBinding
import com.woody.ui.recyclerview.diffutil.DefaultViewDataDiffUtil
import com.woody.ui.recyclerview.viewholder.BaseViewHolder
import com.woody.ui.recyclerview.viewholder.FooterEndViewHolder
import com.woody.ui.recyclerview.viewholder.FooterLoadingViewHolder
import com.woody.ui.recyclerview.viewholder.ViewHolderViewType
import com.woody.ui.recyclerview.viewholder.data.FooterEndViewHolderData
import com.woody.ui.recyclerview.viewholder.data.FooterLoadingViewHolderData
import com.woody.ui.recyclerview.viewholder.data.UnknownViewHolder
import com.woody.ui.recyclerview.viewholder.data.ViewHolderData

class FooterAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    enum class ViewType : ViewHolderViewType {
        LOADING, END, UNKNOWN
    }

    private val items = arrayListOf<ViewHolderData<ViewType>>()

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.viewType?.ordinal ?: ViewType.UNKNOWN.ordinal
    }

    fun visibleLoading(visible: Boolean) {
        val newList = if (visible) {
            arrayListOf(FooterLoadingViewHolderData(Unit))
        } else {
            emptyList()
        }
        DiffUtil.calculateDiff(
            DefaultViewDataDiffUtil(
                oldList = items,
                newList = newList
            )
        ).apply {
            items.clear()
            items.addAll(newList)
            dispatchUpdatesTo(this@FooterAdapter)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (ViewType.values()[viewType]) {
            ViewType.LOADING -> {
                FooterLoadingViewHolder(
                    binding = ItemFooterLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ViewType.END -> {
                FooterEndViewHolder(
                    binding = ItemFooterEndBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ViewType.UNKNOWN -> {
                UnknownViewHolder(parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = items.getOrNull(position)
        when {
            holder is FooterLoadingViewHolder && item is FooterLoadingViewHolderData -> {
                holder.onBindViewHolder(Unit)
            }
            holder is FooterEndViewHolder && item is FooterEndViewHolderData -> {
                holder.onBindViewHolder(item.message)
            }
        }
    }
}