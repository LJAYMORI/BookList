package com.woody.detail.navigation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.woody.detail.R
import com.woody.detail.databinding.FragmentBookDetailBinding
import com.woody.module_ui.image.loadUrl
import com.woody.util.toCurrency
import kotlinx.parcelize.Parcelize

class BookDetailFragment : Fragment() {

    @Parcelize
    data class Args(
        val title: String,
        val author: String,
        val isbn: String,
        val price: String,
        val image: String,
        val publisher: String,
        val pubdate: String,
        val discount: String,
        val description: String
    ) : Parcelable

    companion object {
        private const val KEY_ARGS = "key_args"

        fun newInstance(args: Args): BookDetailFragment {
            return BookDetailFragment().apply {
                arguments = bundleOf(KEY_ARGS to args)
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
        arguments?.getParcelable<Args>(KEY_ARGS)?.let { args ->
            binding.bookDetailImage.loadUrl(args.image)
            binding.bookDetailTitle.text = args.title
            binding.bookDetailAuthor.text = args.author
            binding.bookDetailPrice.text = when {
                args.discount.isNotBlank() -> {
                    getString(R.string.detail_price_s, args.discount.toCurrency())
                }
                args.price.isNotBlank() -> {
                    getString(R.string.detail_price_s, args.price.toCurrency())
                }
                else -> {
                    getString(R.string.detail_has_no_price)
                }
            }
            binding.bookDetailPublisher.text = args.publisher
            binding.bookDetailIsbn.text = getString(R.string.detail_isbn_s, args.isbn)
            binding.bookDetailPubdate.text = getString(R.string.detail_publish_date_s, args.pubdate)
            binding.bookDetailDescription.text = args.description
        }
    }
}