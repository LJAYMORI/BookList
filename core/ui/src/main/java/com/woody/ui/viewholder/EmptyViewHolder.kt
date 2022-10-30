package com.woody.ui.viewholder

import android.content.Context
import android.view.View

class EmptyViewHolder(context: Context) : BaseViewHolder<Unit>(View(context)) {
    override fun onBindViewHolder(data: Unit) {
        // do nothing
    }
}