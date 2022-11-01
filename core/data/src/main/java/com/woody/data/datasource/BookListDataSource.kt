package com.woody.data.datasource

import com.woody.model.BookModel
import java.util.Collections

interface BookListDataSource {
    fun getBookList(): List<BookModel>
    fun addBookList(bookList: List<BookModel>)
    fun clearBookList()
}

class BookListLocalDataSource(
    private val savedBookList: MutableList<BookModel> = Collections.synchronizedList(arrayListOf())
) : BookListDataSource {
    override fun getBookList(): List<BookModel> {
        return savedBookList
    }

    override fun addBookList(bookList: List<BookModel>) {
        savedBookList.addAll(bookList)
    }

    override fun clearBookList() {
        savedBookList.clear()
    }
}