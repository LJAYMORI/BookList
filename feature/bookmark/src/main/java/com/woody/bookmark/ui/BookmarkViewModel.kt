package com.woody.bookmark.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woody.domain.usecase.GetBookmarkBookListUseCase
import com.woody.ui.mapper.mapToBookListData
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.disposables.CompositeDisposable

class BookmarkViewModel(
    private val getBookmarkBookListUseCase: GetBookmarkBookListUseCase
) : ViewModel() {

    private val _bookmarkedListLiveData = MutableLiveData<List<BookListViewHolderData>>()
    val bookmarkedListLiveData: LiveData<List<BookListViewHolderData>> = _bookmarkedListLiveData

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun requestBookmarks() {
        getBookmarkBookListUseCase.invoke()
            .mapToBookListData()
            .subscribe({ list ->
                _bookmarkedListLiveData.value = list
            }, { e ->
                Log.e("asdf", "", e)
            })
            .let { disposable.add(it) }
    }
}