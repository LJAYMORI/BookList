package com.woody.ui.recyclerview.viewholder.data

import com.woody.ui.recyclerview.adapter.FooterAdapter

data class FooterEndViewHolderData(
    val message: String
) : ViewHolderData<FooterAdapter.ViewType> {
    override val viewType: FooterAdapter.ViewType
        get() = FooterAdapter.ViewType.END
}