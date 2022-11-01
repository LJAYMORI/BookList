package com.woody.ui.recyclerview.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.woody.ui.recyclerview.viewholder.data.ViewHolderData

internal class DefaultViewDataDiffUtil(
    private val oldList: List<ViewHolderData<*>>,
    private val newList: List<ViewHolderData<*>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList.getOrNull(oldItemPosition)
        val newItem = newList.getOrNull(newItemPosition)
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList.getOrNull(oldItemPosition)
        val newItem = newList.getOrNull(newItemPosition)
        return oldItem == newItem
    }

}