package com.woody.ui.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotifyPositionScrollListener(
    private val triggerLastPosition: Int = 3,
    private val triggeredAction: () -> Unit
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        when (val layoutManager = recyclerView.layoutManager) {
            is LinearLayoutManager -> {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                val totalCount = layoutManager.itemCount
                if (dy > 0 && totalCount > 0 && lastPosition > totalCount - triggerLastPosition) {
                    triggeredAction.invoke()
                }
            }
        }
    }
}