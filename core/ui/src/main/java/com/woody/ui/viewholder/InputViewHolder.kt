package com.woody.ui.viewholder

import androidx.core.widget.addTextChangedListener
import com.woody.ui.adapter.InputListAdapter
import com.woody.ui.databinding.ItemInputBinding

class InputViewHolder(
    private val binding: ItemInputBinding,
    textChangedAction: (String) -> Unit
    ) : BaseViewHolder<InputViewHolder.Data>(binding.root) {

    data class Data(
        override val viewType: InputListAdapter.ViewType = InputListAdapter.ViewType.INPUT,
        val hint: String = "",
        val defaultQuery: String = ""
    ) : InputListAdapter.InputListData

    init {
        binding.inputListItem.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                (text?.toString() ?: "").let { changedText ->
                    query = changedText
                    textChangedAction.invoke(changedText)
                }
            }
        )
    }

    private var query: String = ""

    override fun onBindViewHolder(data: Data) {
        binding.inputListItem.hint = data.hint
        binding.inputListItem.setText(query)
        binding.inputListItem.setSelection(query.length)
    }
}