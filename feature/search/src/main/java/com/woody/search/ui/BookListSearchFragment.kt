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
import com.woody.ui.recyclerview.adapter.InputListAdapter
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListSearchFragment : BaseFragment() {

    companion object {
        fun newInstance(): BookListSearchFragment {
            return BookListSearchFragment()
        }

        private const val KEY_LIST_SAVED_INSTANCE = "key_list_saved_instance"
        private const val KEY_OLD_QUERY = "key_old_query"
        private const val KEY_BOOK_ITEMS = "key_book_items"
    }

    private lateinit var binding: FragmentBookListSearchBinding
    private val viewModel: BookListSearchViewModel by viewModel()

    private lateinit var inputAdapter: InputListAdapter
    private lateinit var bookListAdapter: BookListAdapter

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
//        initView(savedInstanceState)
//        initViewModel(savedInstanceState)
        initView(null)
        initViewModel(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            KEY_LIST_SAVED_INSTANCE,
            binding.bookListSearchRecyclerView.layoutManager?.onSaveInstanceState()
        )
        outState.putString(
            KEY_OLD_QUERY,
            inputAdapter.currentQuery
        )
        outState.putParcelableArrayList(
            KEY_BOOK_ITEMS,
            arrayListOf(*bookListAdapter.getBookListDataList().toTypedArray())
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        inputAdapter = InputListAdapter(
            textChangedAction = { query ->
                viewModel.search(query)
            }
        ).apply {
            init(
                query = savedInstanceState?.getString(KEY_OLD_QUERY) ?: "",
                hint = getString(R.string.book_list_input_hint)
            )
        }
        bookListAdapter = BookListAdapter(
            itemClickAction = { data ->
                viewModel.onClickedItem(data)
            },
            bookmarkClickAction = { data ->
                viewModel.onClickedBookmark(data)
            }
        ).apply {
            savedInstanceState?.getParcelableArrayList<BookListViewHolderData>(KEY_BOOK_ITEMS)?.let {
                setItems(it)
            }
        }

        binding.bookListSearchRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            savedInstanceState?.getParcelable<Parcelable>(KEY_LIST_SAVED_INSTANCE)?.let {
                onRestoreInstanceState(it)
            }
        }
        binding.bookListSearchRecyclerView.adapter = ConcatAdapter(inputAdapter, bookListAdapter)
        binding.bookListSearchRecyclerView.addOnScrollListener(
            NotifyPositionScrollListener {
                viewModel.requestNextPage()
            }
        )
    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        repeatOnStarted {
            viewModel.pageListFlow.collect { list ->
                bookListAdapter.setItems(list)
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