package com.woody.search.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woody.domain.usecase.RequestBookListUseCase
import com.woody.ui.mapper.BookListDataMapper.toBookListViewHolderData
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
    private val requestBookListUseCase: RequestBookListUseCase
) : ViewModel() {

    private val _pageListFlow = MutableStateFlow(emptyList<BookListViewHolderData>() to true)
    val pageListFlow = _pageListFlow.asStateFlow()

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
                    resultModel?.items?.let { list ->
                        _pageListFlow.emit(list.map { it.toBookListViewHolderData() } to true)
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
                    resultModel?.items?.let { list ->
                        _pageListFlow.emit(list.map { it.toBookListViewHolderData() } to false)
                    }
                    _loadingFlow.emit(resultModel?.items?.size?.let { it > 0 } ?: false)
                }
            }, { e ->
                Log.e("requestNextPageSubject", "", e)
            })
            .let { disposable.add(it) }
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
}