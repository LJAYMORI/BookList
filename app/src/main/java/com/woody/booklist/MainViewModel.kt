package com.woody.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _helloLiveData = MutableLiveData<String>()
    val helloLiveData: LiveData<String> = _helloLiveData

    fun asdf() {
        _helloLiveData.value = "Hello~"
    }

    override fun onCleared() {
        super.onCleared()
    }
}