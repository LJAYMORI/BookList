package com.woody.search

import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData


interface BookListSearchCallback {
    fun onClickBookItem(viewData: BookListViewHolderData)
}