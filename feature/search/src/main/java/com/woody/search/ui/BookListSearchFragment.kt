package com.woody.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.woody.search.BookListSearchCallback
import com.woody.search.R
import com.woody.search.databinding.FragmentBookListSearchBinding
import com.woody.ui.recyclerview.adapter.BookListAdapter
import com.woody.ui.recyclerview.adapter.InputListAdapter
import com.woody.ui.recyclerview.NotifyPositionScrollListener
import com.woody.ui.keyboard.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListSearchFragment : Fragment() {

    private lateinit var binding: FragmentBookListSearchBinding
    private val viewModel: BookListSearchViewModel by viewModel()

    private val inputAdapter: InputListAdapter by lazy {
        InputListAdapter(
            textChangedAction = { query ->
                viewModel.search(query)
            }
        )
    }

    private val bookListAdapter: BookListAdapter by lazy {
        BookListAdapter(
            itemClickAction = { data ->
                viewModel.onItemClick(data)
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
        binding = FragmentBookListSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        inputAdapter.init(
            query = "",
            hint = getString(R.string.book_list_input_hint)
        )

        binding.bookListSearchRecyclerView.adapter = ConcatAdapter(inputAdapter, bookListAdapter)
        binding.bookListSearchRecyclerView.addOnScrollListener(
            NotifyPositionScrollListener {
                viewModel.requestNextPage()
            }
        )
    }

    private fun initViewModel() {
        viewModel.firstPageLiveData.observe { list ->
            bookListAdapter.setItems(list)
        }

        viewModel.nextPageLiveData.observe { list ->
            bookListAdapter.addItems(list)
        }

        viewModel.hideKeyboardLiveData.observe {
            hideKeyboard()
        }

        viewModel.openDetailPageLiveData.observe { data ->
            (activity as? BookListSearchCallback)?.onClickBookItem(
                title = data.title,
                author = data.author,
                isbn = data.isbn,
                price = "data.price",
                image = data.image,
                publisher = "data.publisher",
                pubdate = "data.pubdate",
                discount = "data.discount",
                description = data.description,
            )
        }
    }

    private fun <T> LiveData<T>.observe(observer: Observer<in T>) {
        this.observe(viewLifecycleOwner, observer)
    }
}