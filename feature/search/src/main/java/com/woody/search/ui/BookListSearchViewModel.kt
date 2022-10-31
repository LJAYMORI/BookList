package com.woody.search.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woody.data.entity.BookEntity
import com.woody.domain.usecase.BookmarkUseCase
import com.woody.domain.usecase.GetBookListUseCase
import com.woody.ui.mapper.mapToBookListData
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BookListSearchViewModel(
    private val getBookListUseCase: GetBookListUseCase,
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private val _firstPageLiveData = MutableLiveData<List<BookListViewHolderData>>()
    val firstPageLiveData: LiveData<List<BookListViewHolderData>> = _firstPageLiveData

    private val _nextPageLiveData = MutableLiveData<List<BookListViewHolderData>>()
    val nextPageLiveData: LiveData<List<BookListViewHolderData>> = _nextPageLiveData

    private val _hideKeyboardLiveData = MutableLiveData<Unit>()
    val hideKeyboardLiveData: LiveData<Unit> = _hideKeyboardLiveData

    private val _openDetailPageLiveData = MutableLiveData<BookListViewHolderData>()
    val openDetailPageLiveData: LiveData<BookListViewHolderData> = _openDetailPageLiveData

    private val querySubject = PublishSubject.create<String>()
    private val requestNextPageSubject = PublishSubject.create<Unit>()

    private val disposable = CompositeDisposable()

    init {
        querySubject.filter { query -> query.isNotEmpty() }
            .debounce(1L, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .flatMapSingle { query ->
                getBookListUseCase.invoke(query)
                    .mapToBookListData()
            }
            .subscribe({ list ->
                _firstPageLiveData.value = list
                _hideKeyboardLiveData.value = Unit
            }, { e ->
                Log.e("querySubject", "", e)
            })
            .let { disposable.add(it) }

        requestNextPageSubject.filter { getBookListUseCase.isDisposed() }
            .flatMapSingle {
                getBookListUseCase.invoke()
                    .mapToBookListData()
            }
            .subscribe({ list ->
                _nextPageLiveData.value = list
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

    fun onItemClick(data: BookListViewHolderData) {
        _openDetailPageLiveData.value = data
        bookmarkUseCase.invoke(
            entity = BookEntity(
                title = data.title,
                author = data.author,
                isbn = data.isbn,
                price = "data.price",
                image = data.image,
                publisher = "data.publisher",
                pubdate = "data.pubdate",
                discount = "data.discount",
                description = data.description,
            ),
            flag = !data.isBookmarked
        ).subscribe({
            Log.d("Asdf", "Bookmarked")
        }, { e ->
            Log.e("Asdf", "", e)
        }).let { disposable.add(it) }
    }
}