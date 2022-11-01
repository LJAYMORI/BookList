package com.woody.booklist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.woody.booklist.R
import com.woody.booklist.databinding.ActivityMainBinding
import com.woody.detail.ui.BookDetailFragment
import com.woody.search.BookListSearchCallback
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData

class MainActivity : AppCompatActivity(), BookListSearchCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
    }

    override fun onClickBookItem(viewData: BookListViewHolderData) {
        supportFragmentManager.commit {
            add(R.id.main_fragment_container, BookDetailFragment.newInstance(viewData), "tag")
            setReorderingAllowed(true)
            addToBackStack("backstack")
        }
    }
}