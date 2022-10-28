package com.woody.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.woody.search.BookListSearchCallback
import com.woody.search.databinding.FragmentBookListSearchBinding
import com.woody.search.ui.adapter.BookListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListSearchFragment : Fragment() {

    companion object {
        private const val KEY_DEFAULT_QUERY = "key_default_query"

        fun newInstance(defaultQuery: String = ""): BookListSearchFragment {
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
    private val adapter: BookListAdapter by lazy {
        BookListAdapter(
            onItemClickAction = { data ->
                (activity as? BookListSearchCallback)?.onClickBookItem(
                    title = data.title,
                    author = data.author,
                    isbn = data.isbn,
                    price = data.price,
                    image = data.image,
                    publisher = data.publisher,
                    pubdate = data.pubdate,
                    discount = data.discount,
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

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.searchBookListLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setItems(list)
        }

        viewModel.search(arguments?.getString(KEY_DEFAULT_QUERY) ?: "kotlin")
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