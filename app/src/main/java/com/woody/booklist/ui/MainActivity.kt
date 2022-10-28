package com.woody.booklist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.woody.booklist.R
import com.woody.booklist.databinding.ActivityMainBinding
import com.woody.bookmark.BookmarkFragment
import com.woody.detail.BookDetailActivity
import com.woody.detail.navigation.BookDetailFragment
import com.woody.search.BookListSearchCallback
import com.woody.search.ui.BookListSearchFragment

class MainActivity : AppCompatActivity(), BookListSearchCallback {

    companion object {
        private const val TAG_SEARCH_LIST_FRAGMENT = "tag_search_list_fragment"
        private const val TAG_BOOKMARK_FRAGMENT = "tag_bookmark_fragment"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            initView()
        }
    }

    private fun initView() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            return@setOnItemSelectedListener when (menuItem.itemId) {
                R.id.menu_bottom_navigation_search -> {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(TAG_SEARCH_LIST_FRAGMENT)
                            ?: BookListSearchFragment.newInstance("")
                    supportFragmentManager.commit {
                        replace(
                            R.id.main_fragment_container,
                            fragment,
                            TAG_SEARCH_LIST_FRAGMENT
                        )
                        setReorderingAllowed(true)
                        addToBackStack(TAG_SEARCH_LIST_FRAGMENT)
                    }
                    true
                }
                R.id.menu_bottom_navigation_bookmark -> {
                    val fragment = supportFragmentManager.findFragmentByTag(TAG_BOOKMARK_FRAGMENT)
                        ?: BookmarkFragment.newInstance()
                    supportFragmentManager.commit {
                        replace(
                            R.id.main_fragment_container,
                            fragment,
                            TAG_BOOKMARK_FRAGMENT
                        )
                        setReorderingAllowed(true)
                        addToBackStack(TAG_BOOKMARK_FRAGMENT)
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onClickBookItem(
        title: String,
        author: String,
        isbn: String,
        price: String,
        image: String,
        publisher: String,
        pubdate: String,
        discount: String,
        description: String
    ) {
        startActivity(
            BookDetailActivity.newIntent(
                this,
                BookDetailFragment.Args(
                    title = title,
                    author = author,
                    isbn = isbn,
                    price = price,
                    image = image,
                    publisher = publisher,
                    pubdate = pubdate,
                    discount = discount,
                    description = description
                )
            )
        )
    }

}