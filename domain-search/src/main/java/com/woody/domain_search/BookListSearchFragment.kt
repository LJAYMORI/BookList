package com.woody.domain_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.woody.domain_search.databinding.FragmentBookListSearchBinding

class BookListSearchFragment : Fragment() {

    private lateinit var binding: FragmentBookListSearchBinding

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
        Toast.makeText(requireContext(), "Hello fragment", Toast.LENGTH_SHORT).show()
        binding.bookListSearchRecyclerView
    }
}