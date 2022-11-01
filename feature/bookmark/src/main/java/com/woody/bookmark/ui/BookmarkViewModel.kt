package com.woody.bookmark.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woody.domain.usecase.BookmarkUseCase
import com.woody.domain.usecase.GetBookmarkedBookListUseCase
import com.woody.ui.mapper.BookListDataMapper.toModel
import com.woody.ui.mapper.mapToBookListData
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    getBookmarkedBookListUseCase: GetBookmarkedBookListUseCase,
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private val _bookmarkedListFlow = MutableStateFlow(emptyList<BookListViewHolderData>())
    val bookmarkedListFlow = _bookmarkedListFlow.asStateFlow()

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

    fun onClickUnBookmark(data: BookListViewHolderData) {
        bookmarkUseCase.invoke(data.toModel(), false)
            .subscribe({
                // do nothing..?
            }, { e ->
                Log.e("asdf", "", e)
            }).let { disposable.add(it) }
    }
}