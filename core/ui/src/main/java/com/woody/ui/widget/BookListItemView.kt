package com.woody.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.woody.ui.databinding.ViewBookListItemBinding
import com.woody.ui.image.loadUrl

class BookListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewBookListItemBinding =
        ViewBookListItemBinding.inflate(LayoutInflater.from(context), this)

    fun updateImage(imageUrl: String) {
        binding.bookListImage.loadUrl(imageUrl)
    }

    fun updateTitle(title: String) {
        binding.bookListTitle.text = title
    }

    fun updateAuthor(author: String) {
        binding.bookListAuthor.text = author
    }

    fun updateDescription(description: String) {
        binding.bookListDescription.text = description
    }
}