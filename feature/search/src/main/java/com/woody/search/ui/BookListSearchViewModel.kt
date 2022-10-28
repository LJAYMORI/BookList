package com.woody.search.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woody.domain.usecase.GetBookListUseCase
import com.woody.search.ui.data.BookListData
import com.woody.search.ui.data.transformToBookListData
import io.reactivex.disposables.CompositeDisposable

class BookListSearchViewModel constructor(
    private val getBookListUseCase: GetBookListUseCase
) : ViewModel() {

    private val _searchBookListLiveData = MutableLiveData<List<BookListData>>()
    val searchBookListLiveData: LiveData<List<BookListData>> = _searchBookListLiveData

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun search(query: String) {
        getBookListUseCase.invoke(query)
            .transformToBookListData()
            .subscribe({ list ->
                _searchBookListLiveData.value = list
            }, { e ->
                Log.e("asdf", "", e)
            })
            .let { disposable.add(it) }
    }
}