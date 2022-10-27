package com.woody.domain_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.woody.domain_search.databinding.FragmentBookListSearchBinding
import com.woody.domain_search.datasource.BookListApi
import com.woody.domain_search.datasource.BookListRemoteDataSource
import com.woody.domain_search.repository.BookListRepositoryImpl
import com.woody.network.HeaderInterceptor
import com.woody.network.NetworkFactory
import io.reactivex.schedulers.Schedulers

class BookListSearchFragment : Fragment() {

    private lateinit var binding: FragmentBookListSearchBinding

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
        Toast.makeText(requireContext(), "Hello fragment", Toast.LENGTH_SHORT).show()
        binding.bookListSearchRecyclerView

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
            .subscribe({ vo ->
                Log.d("BookListSearchFrag", vo.toString())
            }, { e ->
                Log.e("BookListSearchFrag", "", e)
            })
    }
}