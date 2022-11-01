package com.woody.bookmark.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ConcatAdapter
import com.woody.bookmark.BookmarkCallback
import com.woody.bookmark.R
import com.woody.bookmark.databinding.FragmentBookmarkBinding
import com.woody.ui.base.BaseFragment
import com.woody.ui.recyclerview.adapter.BookListAdapter
import com.woody.ui.recyclerview.adapter.FooterAdapter
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
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

    private fun showBookmarkCancelDialog(data: BookListViewHolderData) {
        val context = context ?: return
        AlertDialog.Builder(context)
            .setTitle(R.string.bookmarks_cancel_alert_title)
            .setMessage(R.string.bookmarks_cancel_alert_description)
            .setPositiveButton(R.string.bookmarks_cancel_alert_positive) { _, _ ->
                viewModel.onClickUnBookmark(data)
            }
            .setNegativeButton(R.string.bookmarks_cancel_alert_negative) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}