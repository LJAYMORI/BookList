package com.woody.bookmark

import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData

interface BookmarkCallback {
    fun onClickedBookmarkItem(viewData: BookListViewHolderData)
}