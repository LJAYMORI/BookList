package com.woody.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.woody.detail.R
import com.woody.detail.databinding.FragmentBookDetailBinding
import com.woody.ui.base.BaseFragment
import com.woody.ui.image.loadUrl
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData
import com.woody.util.toCurrency

class BookDetailFragment : BaseFragment() {

    companion object {
        private const val KEY_VIEW_DATA = "key_view_data"

        fun newInstance(data: BookListViewHolderData): BookDetailFragment {
            return BookDetailFragment().apply {
                arguments = bundleOf(KEY_VIEW_DATA to data)
            }
        }
    }

    private lateinit var binding: FragmentBookDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        arguments?.getParcelable<BookListViewHolderData>(KEY_VIEW_DATA)?.let { viewData ->
            binding.bookDetailImage.loadUrl(viewData.image)
            binding.bookDetailTitle.text = viewData.title
            binding.bookDetailAuthor.text = viewData.author
            binding.bookDetailPrice.text = when {
                viewData.discount.isNotBlank() -> {
                    getString(R.string.detail_price_s, viewData.discount.toCurrency())
                }
                viewData.price.isNotBlank() -> {
                    getString(R.string.detail_price_s, viewData.price.toCurrency())
                }
                else -> {
                    getString(R.string.detail_has_no_price)
                }
            }
            binding.bookDetailPublisher.text = viewData.publisher
            binding.bookDetailIsbn.text = getString(R.string.detail_isbn_s, viewData.isbn)
            binding.bookDetailPubdate.text = getString(R.string.detail_publish_date_s, viewData.pubdate)
            binding.bookDetailDescription.text = viewData.description
        }
    }
}