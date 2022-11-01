package com.woody.bookmark.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woody.domain.usecase.GetBookmarkedBookListUseCase
import com.woody.ui.mapper.mapToBookListData
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    getBookmarkedBookListUseCase: GetBookmarkedBookListUseCase
) : ViewModel() {

//    private val _bookmarkedListLiveData = MutableStateFlow(emptyList<BookListViewHolderData>())
//    val bookmarkedListLiveData = _bookmarkedListLiveData.asStateFlow()
    private val _bookmarkedListFlow = MutableSharedFlow<List<BookListViewHolderData>>()
    val bookmarkedListFlow = _bookmarkedListFlow.asSharedFlow()

    private val _openBookDetailFlow = MutableSharedFlow<BookListViewHolderData>()
    val openBookDetailFlow = _openBookDetailFlow.asSharedFlow()

    private val disposable = CompositeDisposable()

    init {
        getBookmarkedBookListUseCase.invoke(Unit)
            .mapToBookListData()
            .subscribe({ list ->
                viewModelScope.launch {
                    _bookmarkedListFlow.emit(list)
                }
            }, { e ->
                Log.e("asdf", "", e)
            })
            .let { disposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun onClickItem(data: BookListViewHolderData) {
        viewModelScope.launch {
            _openBookDetailFlow.emit(data)
        }
    }

    fun onClickBookmark(data: BookListViewHolderData) {

    }
}