package com.woody.search.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woody.domain.usecase.BookmarkUseCase
import com.woody.domain.usecase.GetBookListUseCase
import com.woody.domain.usecase.RequestBookListUseCase
import com.woody.domain.usecase.GetBookmarkedBookListUseCase
import com.woody.ui.mapper.BookListDataMapper.toBookListViewHolderData
import com.woody.ui.mapper.BookListDataMapper.toModel
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookListSearchViewModel(
    private val requestBookListUseCase: RequestBookListUseCase,
    getBookListUseCase: GetBookListUseCase,
    private val getBookmarkedListUseCase: GetBookmarkedBookListUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

//    private val _pageListFlow = MutableStateFlow(emptyList<BookListViewHolderData>())
//    val pageListFlow = _pageListFlow.asStateFlow()
    private val _pageListFlow = MutableSharedFlow<List<BookListViewHolderData>>()
    val pageListFlow = _pageListFlow.asSharedFlow()

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow = _loadingFlow.asStateFlow()

    private val _hideKeyboardFlow = MutableSharedFlow<Unit>()
    val hideKeyboardFlow = _hideKeyboardFlow.asSharedFlow()

    private val _openDetailPageFlow = MutableSharedFlow<BookListViewHolderData>()
    val openDetailPageFlow = _openDetailPageFlow.asSharedFlow()



    private val querySubject = PublishSubject.create<String>()
    private val requestNextPageSubject = PublishSubject.create<Unit>()

    private val disposable = CompositeDisposable()

    init {
        querySubject.debounce(1L, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .flatMapSingle { query ->
                requestBookListUseCase.invoke(query)
            }
            .subscribe({ result ->
                val resultModel = result.getOrNull()
                viewModelScope.launch {
                    _hideKeyboardFlow.emit(Unit)
                    resultModel?.run {
                        _loadingFlow.emit(total != items.size)
                    }
                }
            }, { e ->
                Log.e("querySubject", "", e)
            })
            .let { disposable.add(it) }

        requestNextPageSubject.filter { requestBookListUseCase.isDisposed() }
            .flatMapSingle {
                requestBookListUseCase.invoke()
            }
            .subscribe({ result ->
                val resultModel = result.getOrNull()
                viewModelScope.launch {
                    _loadingFlow.emit(resultModel?.items?.size?.let { it > 0 } ?: false)
                }
            }, { e ->
                Log.e("requestNextPageSubject", "", e)
            })
            .let { disposable.add(it) }

        getBookListUseCase.invoke(Unit)
            .map { it.getOrNull() ?: emptyList() }
            .flatMapSingle { bookList ->
                getBookmarkedListUseCase.invoke(Unit)
                    .map { result ->
                        bookList to (result.getOrNull()?.map { it.isbn } ?: emptyList())
                    }
                    .first(bookList to emptyList())
            }
            .map { (bookList, bookmarkList) ->
                bookList.map { model ->
                    model.toBookListViewHolderData(
                        bookmarked = bookmarkList.find { isbn ->
                            isbn == model.isbn
                        } != null
                    )
                }
            }
            .subscribe({ list ->
                viewModelScope.launch {
                    _pageListFlow.emit(list)
                }
            }, { e ->
                Log.e("asdf", "", e)
            }).let { disposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun search(query: String) {
        querySubject.onNext(query)
    }

    fun requestNextPage() {
        requestNextPageSubject.onNext(Unit)
    }

    fun onClickedItem(data: BookListViewHolderData) {
        viewModelScope.launch {
            _openDetailPageFlow.emit(data)
        }
    }

    fun onClickedBookmark(data: BookListViewHolderData) {
        val toggledFlag = !data.isBookmarked
        bookmarkUseCase.invoke(data.toModel(), toggledFlag)
            .subscribe({

            }, { e ->
                Log.e("asdf", "", e)
            }).let { disposable.add(it) }
    }
}