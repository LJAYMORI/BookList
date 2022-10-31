package com.woody.ui.recyclerview.viewholder.data

import com.woody.ui.recyclerview.viewholder.ViewHolderViewType

interface ViewHolderData<T : ViewHolderViewType> {
    val viewType: T
}