package com.woody.ui.recyclerview.viewholder.data

import android.os.Parcelable
import com.woody.ui.recyclerview.adapter.InputListAdapter
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookNameInputViewHolderData(
    val hint: String,
    val defaultQuery: String = ""
) : ViewHolderData<InputListAdapter.ViewType>, Parcelable {
    override val viewType: InputListAdapter.ViewType
        get() = InputListAdapter.ViewType.INPUT
}