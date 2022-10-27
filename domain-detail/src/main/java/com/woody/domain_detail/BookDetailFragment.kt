package com.woody.domain_detail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.woody.domain_detail.databinding.FragmentBookDetailBinding
import com.woody.module_ui.image.loadUrl
import kotlinx.parcelize.Parcelize

class BookDetailFragment : Fragment() {

    @Parcelize
    data class Argument(
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

    private lateinit var binding: FragmentBookDetailBinding
    private val safeArgs: BookDetailFragmentArgs by navArgs()

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
        safeArgs.argsBookDetailImage?.let { args ->
            binding.bookDetailImage.loadUrl(args.image)
            binding.bookDetailTitle.text = args.title
            binding.bookDetailAuthor.text = args.author
            binding.bookDetailPrice.text = "Price: ${args.price} / Discount: ${args.discount}"
            binding.bookDetailPublisher.text = args.publisher
            binding.bookDetailIsbn.text = args.isbn
            binding.bookDetailPubdate.text = args.pubdate
            binding.bookDetailDescription.text = args.description
        }
    }
}