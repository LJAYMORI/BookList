package com.woody.ui.recyclerview.viewholder

import com.woody.ui.databinding.ItemFooterEndBinding

class FooterEndViewHolder(
    private val binding: ItemFooterEndBinding
) : BaseViewHolder<String>(binding.root) {
    override fun onBindViewHolder(data: String) {
        binding.endText.text = data
    }
}