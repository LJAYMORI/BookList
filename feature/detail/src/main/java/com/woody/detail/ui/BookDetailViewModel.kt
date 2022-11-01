package com.woody.detail.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woody.domain.usecase.BookmarkUseCase
import com.woody.domain.usecase.IsBookmarkedUseCase
import com.woody.ui.mapper.BookListDataMapper.toModel
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val isBookmarkedUseCase: IsBookmarkedUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    private val _bookmarkChangeFlow = MutableStateFlow(false)
    val bookmarkChangeFlow = _bookmarkChangeFlow.asStateFlow()

    private val checkBookmarkSubject = PublishSubject.create<String>()

    private val disposable = CompositeDisposable()

    init {
        checkBookmarkSubject.filter { bookmarkUseCase.isDisposed() }
            .flatMapSingle { isbn ->
                isBookmarkedUseCase.invoke(isbn)
            }
            .map { result -> result.getOrNull() == true }
            .subscribe({ isBookmarked ->
                viewModelScope.launch {
                    _bookmarkChangeFlow.emit(isBookmarked)
                }
            }, {
                Log.e("asdf", "", it)
            })
            .let { disposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun checkBookmarked(isbn: String) {
        checkBookmarkSubject.onNext(isbn)
    }

    fun onClickedBookmark(viewData: BookListViewHolderData) {
        isBookmarkedUseCase.invoke(viewData.isbn)
            .map { result -> result.getOrNull() == true }
            .flatMapCompletable { isBookmarked ->
                bookmarkUseCase.invoke(viewData.toModel(), !isBookmarked)
            }
            .subscribe({
                checkBookmarked(viewData.isbn)
            }, {
                Log.e("asdf", "", it)
            })
            .let { disposable.add(it) }
    }
}