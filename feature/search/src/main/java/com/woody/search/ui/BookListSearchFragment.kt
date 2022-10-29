package com.woody.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.woody.search.R
import com.woody.search.databinding.FragmentBookListSearchBinding
import com.woody.ui.adapter.BookListAdapter
import com.woody.ui.adapter.InputListAdapter
import com.woody.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListSearchFragment : Fragment() {

    companion object {
        private const val KEY_DEFAULT_QUERY = "key_default_query"

        fun newInstance(defaultQuery: String = "kotlin"): BookListSearchFragment {
            return BookListSearchFragment().apply {
                arguments = bundleOf(KEY_DEFAULT_QUERY to defaultQuery)
            }
        }

        private const val TAG = "BookListSearchFragment"
        private const val KEY_LAYOUT_MANAGER_SAVE_INSTANCE_STATE = "key_layout_manager_save_instance_state"
        private const val KEY_ADAPTER_LIST = "key_adapter_list"
    }

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
            itemClickAction = { isbn ->
                viewModel.onClickItem(isbn)
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
        if (savedInstanceState == null) {
            initView()
            initViewModel()
        }
    }

    private fun initView() {
        val defaultQuery = arguments?.getString(KEY_DEFAULT_QUERY) ?: ""
        if (defaultQuery.isNotEmpty()) {
            arguments?.remove(KEY_DEFAULT_QUERY)
        }
        inputAdapter.init(
            query = defaultQuery,
            hint = getString(R.string.book_list_input_hint)
        )

        binding.bookListSearchRecyclerView.adapter = ConcatAdapter(inputAdapter, bookListAdapter)
    }

    private fun initViewModel() {
        viewModel.searchBookListLiveData.observe { list ->
            bookListAdapter.setItems(list)
        }

        viewModel.hideKeyboardLiveData.observe {
            hideKeyboard()
        }
    }

    private fun <T> LiveData<T>.observe(observer: Observer<in T>) {
        this.observe(viewLifecycleOwner, observer)
    }
}