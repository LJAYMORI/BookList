package com.woody.bookmark.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import com.woody.bookmark.BookmarkCallback
import com.woody.bookmark.databinding.FragmentBookmarkBinding
import com.woody.ui.base.BaseFragment
import com.woody.ui.recyclerview.adapter.BookListAdapter
import com.woody.ui.recyclerview.adapter.FooterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : BaseFragment() {

    companion object {
        fun newInstance(): BookmarkFragment {
            return BookmarkFragment()
        }
    }

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModel()

    private val bookListAdapter: BookListAdapter by lazy {
        BookListAdapter(
            itemClickAction = { data ->
                viewModel.onClickItem(data)
            },
            bookmarkClickAction = { data ->
                viewModel.onClickBookmark(data)
            }
        )
    }

    private val footerAdapter: FooterAdapter by lazy {
        FooterAdapter()
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
    }

    private fun initView() {
        binding.bookmarkRecyclerView.adapter = ConcatAdapter(bookListAdapter, footerAdapter)
    }

    private fun initViewModel() {
        repeatOnStarted {
            viewModel.bookmarkedListFlow.collect { list ->
                bookListAdapter.setItems(list)
            }
        }

        repeatOnStarted {
            viewModel.openBookDetailFlow.collect { data ->
                (activity as? BookmarkCallback)?.onClickedBookmarkItem(data)
            }
        }
    }
}