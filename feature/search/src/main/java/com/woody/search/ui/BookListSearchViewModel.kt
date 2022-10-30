package com.woody.search.ui

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woody.data.entity.SearchResultEntity
import com.woody.domain.usecase.GetBookListUseCase
import com.woody.ui.adapter.BookListAdapter
import com.woody.ui.viewholder.BookListItemViewHolder
import com.woody.ui.viewholder.BookListMessageViewHolder
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BookListSearchViewModel constructor(
    private val getBookListUseCase: GetBookListUseCase
) : ViewModel() {

    private val _firstPageLiveData = MutableLiveData<List<BookListAdapter.BookListData>>()
    val firstPageLiveData: LiveData<List<BookListAdapter.BookListData>> = _firstPageLiveData

    private val _nextPageLiveData = MutableLiveData<List<BookListAdapter.BookListData>>()
    val nextPageLiveData: LiveData<List<BookListAdapter.BookListData>> = _nextPageLiveData

    private val _hideKeyboardLiveData = MutableLiveData<Unit>()
    val hideKeyboardLiveData: LiveData<Unit> = _hideKeyboardLiveData

    private val querySubject = PublishSubject.create<String>()
    private val requestNextPageSubject = PublishSubject.create<Unit>()

    private val disposable = CompositeDisposable()

    init {
        querySubject.filter { query -> query.isNotEmpty() }
            .debounce(1L, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .flatMapSingle { query ->
                getBookListUseCase.invoke(query)
                    .transformToBookListData()
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
                    .transformToBookListData()
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

}

@WorkerThread
internal fun Single<Result<SearchResultEntity>>.transformToBookListData(): Single<List<BookListAdapter.BookListData>> {
    return this.map { result ->
        result.getOrNull()?.let { entity ->
            entity.items.map { bookEntity ->
                BookListItemViewHolder.Data(
                    title = bookEntity.title,
                    author = bookEntity.author,
                    isbn = bookEntity.isbn,
                    price = bookEntity.price,
                    image = bookEntity.image,
                    publisher = bookEntity.publisher,
                    pubdate = bookEntity.pubdate,
                    discount = bookEntity.discount,
                    description = bookEntity.description
                )
            }
        } ?: arrayListOf(BookListMessageViewHolder.MessageData(message = "Empty"))
    }
}