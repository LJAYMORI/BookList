package com.woody.ui.recyclerview.viewholder

import androidx.core.widget.addTextChangedListener
import com.woody.ui.databinding.ItemBookNameInputBinding
import com.woody.ui.recyclerview.viewholder.data.BookNameInputViewHolderData

class BookNameInputViewHolder(
    private val binding: ItemBookNameInputBinding,
    textChangedAction: (String) -> Unit
) : BaseViewHolder<BookNameInputViewHolderData>(binding.root) {

    init {
        binding.inputListItem.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                (text?.toString() ?: "").let { changedText ->
                    oldQuery = changedText
                    textChangedAction.invoke(changedText)
                }
            }
        )
    }

    private var oldQuery: String? = null

    override fun onBindViewHolder(data: BookNameInputViewHolderData) {
        binding.inputListItem.hint = data.hint
        binding.inputListItem.setText(oldQuery ?: data.defaultQuery)
        binding.inputListItem.setSelection(oldQuery?.length ?: data.defaultQuery.length)
    }
}