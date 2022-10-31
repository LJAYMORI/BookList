package com.woody.bookmark.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.woody.bookmark.BookmarkCallback
import com.woody.bookmark.databinding.FragmentBookmarkBinding
import com.woody.ui.recyclerview.adapter.BookListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModel()

    private val bookListAdapter: BookListAdapter by lazy {
        BookListAdapter(
            itemClickAction = { data ->
                (activity as? BookmarkCallback)?.onClickBookItem(
                    title = data.title,
                    author = data.author,
                    isbn = data.isbn,
                    price = "",
                    image = data.image,
                    publisher = "",
                    pubdate = "",
                    discount = "",
                    description = data.description,
                )
            },
            bookmarkClickAction = { data ->

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()

        viewModel.requestBookmarks()
    }

    private fun initView() {
        binding.bookmarkRecyclerView.adapter = ConcatAdapter(bookListAdapter)
    }

    private fun initViewModel() {
        viewModel.bookmarkedListLiveData.observe { list ->
            bookListAdapter.setItems(list)
        }
    }

    private fun <T> LiveData<T>.observe(observer: Observer<in T>) {
        this.observe(viewLifecycleOwner, observer)
    }
}