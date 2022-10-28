package com.woody.search.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woody.network.HeaderInterceptor
import com.woody.network.NetworkFactory
import com.woody.search.BookListSearchCallback
import com.woody.search.databinding.FragmentBookListSearchBinding
import com.woody.search.datasource.BookListApi
import com.woody.search.datasource.BookListRemoteDataSource
import com.woody.search.repository.BookListRepositoryImpl
import com.woody.search.ui.adapter.BookListAdapter
import com.woody.search.ui.data.toBookListData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookListSearchFragment : Fragment() {

    companion object {
        private const val TAG = "BookListSearchFragment"
        private const val KEY_LAYOUT_MANAGER_SAVE_INSTANCE_STATE = "key_layout_manager_save_instance_state"
        private const val KEY_ADAPTER_LIST = "key_adapter_list"
    }

    private lateinit var binding: FragmentBookListSearchBinding
    private val adapter: BookListAdapter by lazy {
        BookListAdapter(
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
    }

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

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable(
//            KEY_LAYOUT_MANAGER_SAVE_INSTANCE_STATE,
//            binding.bookListSearchRecyclerView.layoutManager?.onSaveInstanceState()
//        )
//        outState.putParcelableArrayList(
//            KEY_ADAPTER_LIST,
//            arrayListOf(*adapter.list.toTypedArray())
//        )
//    }
}