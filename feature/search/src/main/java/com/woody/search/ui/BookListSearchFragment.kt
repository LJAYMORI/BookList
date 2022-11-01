package com.woody.search.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.woody.search.BookListSearchCallback
import com.woody.search.R
import com.woody.search.databinding.FragmentBookListSearchBinding
import com.woody.ui.base.BaseFragment
import com.woody.ui.keyboard.hideKeyboard
import com.woody.ui.recyclerview.NotifyPositionScrollListener
import com.woody.ui.recyclerview.adapter.BookListAdapter
import com.woody.ui.recyclerview.adapter.FooterAdapter
import com.woody.ui.recyclerview.adapter.InputListAdapter
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListSearchFragment : BaseFragment() {

    companion object {
        private const val KEY_QUERY = "query"
        private const val KEY_LIST_SAVED_INSTANCE = "list_saved_instance"
        private const val KEY_BOOK_LIST = "book_list"
    }

    private lateinit var binding: FragmentBookListSearchBinding
    private val viewModel: BookListSearchViewModel by viewModel()

    private lateinit var inputAdapter: InputListAdapter
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var footerAdapter: FooterAdapter

//    private val inputAdapter by lazy {
//        InputListAdapter(
//            textChangedAction = { query ->
//                viewModel.search(query)
//            }
//        )
//    }
//    private val bookListAdapter by lazy {
//        BookListAdapter(
//            itemClickAction = { data ->
//                viewModel.onClickedItem(data)
//            }
//        )
//    }
//    private val footerAdapter by lazy {
//        FooterAdapter()
//    }

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
        initView(savedInstanceState)
        initViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(
            KEY_QUERY,
            inputAdapter.currentQuery
        )
        outState.putParcelable(
            KEY_LIST_SAVED_INSTANCE,
            binding.bookListSearchRecyclerView.layoutManager?.onSaveInstanceState()
        )
        outState.putParcelableArrayList(
            KEY_BOOK_LIST,
            arrayListOf(*bookListAdapter.getBookListDataList().toTypedArray())
        )
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.bookListSearchRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            savedInstanceState?.getParcelable<Parcelable>(KEY_LIST_SAVED_INSTANCE)?.let {
                onRestoreInstanceState(it)
            }
        }

        inputAdapter = InputListAdapter(
            textChangedAction = { query ->
                viewModel.search(query)
            }
        ).apply {
            init(
                query = savedInstanceState?.getString(KEY_QUERY) ?: "",
                hint = getString(R.string.book_list_input_hint)
            )
        }

        bookListAdapter = BookListAdapter(
            itemClickAction = { data ->
                viewModel.onClickedItem(data)
            }
        ).apply {
            savedInstanceState?.getParcelableArrayList<BookListViewHolderData>(KEY_BOOK_LIST)?.let { list ->
                setItems(list)
            }
        }

        footerAdapter = FooterAdapter()

        binding.bookListSearchRecyclerView.adapter = ConcatAdapter(
            inputAdapter,
            bookListAdapter,
            footerAdapter
        )

        binding.bookListSearchRecyclerView.addOnScrollListener(
            NotifyPositionScrollListener {
                viewModel.requestNextPage()
            }
        )
    }

    private fun initViewModel() {
        repeatOnStarted {
            viewModel.pageListFlow.collect { (list, isFirstPage) ->
                if (isFirstPage) {
                    bookListAdapter.setItems(list)
                } else {
                    bookListAdapter.addItems(list)
                }
            }
        }

        repeatOnStarted {
            viewModel.loadingFlow.collect { visible ->
                footerAdapter.visibleLoading(visible)
            }
        }

        repeatOnStarted {
            viewModel.hideKeyboardFlow.collect {
                hideKeyboard()
            }
        }

        repeatOnStarted {
            viewModel.openDetailPageFlow.collect { data ->
                (activity as? BookListSearchCallback)?.onClickBookItem(data)
            }
        }
    }
}