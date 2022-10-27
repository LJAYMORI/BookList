package com.woody.domain_search.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woody.domain_search.BookListSearchCallback
import com.woody.domain_search.databinding.FragmentBookListSearchBinding
import com.woody.domain_search.datasource.BookListApi
import com.woody.domain_search.datasource.BookListRemoteDataSource
import com.woody.domain_search.repository.BookListRepositoryImpl
import com.woody.domain_search.ui.adapter.BookListAdapter
import com.woody.domain_search.ui.data.toBookEntity
import com.woody.domain_search.ui.data.toBookListData
import com.woody.network.HeaderInterceptor
import com.woody.network.NetworkFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookListSearchFragment : Fragment() {

    companion object {
        private const val TAG = "BookListSearchFragment"
    }

    private lateinit var binding: FragmentBookListSearchBinding
    private lateinit var adapter: BookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookListAdapter(
            onItemClickAction = { data ->
                (activity as? BookListSearchCallback)?.onClickBookItem(
                    title = data.title,
                    author = data.author,
                    isbn = "",
                    price = "",
                    image = data.imageUrl,
                    publisher = "",
                    pubdate = "",
                    discount = "",
                    description = data.description,
                )
            },
            onEmptyClickAction = {

            }
        )
        binding.bookListSearchRecyclerView.adapter = adapter

        val repository = BookListRepositoryImpl(
            BookListRemoteDataSource(
                NetworkFactory.create(
                    baseUrl = "https://openapi.naver.com/",
                    HeaderInterceptor(hashMapOf("X-Naver-Client-Id" to "KiXBNHcPVH9OxOqLAkio", "X-Naver-Client-Secret" to "l5iWGZAZmt"))
                ).create(BookListApi::class.java)
            )
        )

        repository.getBookList(query = "자바", display = 10, start = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ vo ->
                vo.items
                    ?.map { it.toBookListData() }
                    ?.let { list ->
                        adapter.setItems(list)
                    }
            }, { e ->
                Log.e(TAG, "", e)
            })
    }
}