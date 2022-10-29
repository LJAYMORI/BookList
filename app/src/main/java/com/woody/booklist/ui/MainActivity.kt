package com.woody.booklist.ui

import android.os.Bundle
import android.util.Log
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

    private val searchFragmentTag by lazy {
        getString(R.string.navigation_book_search)
    }

    private val bookmarkFragmentTag by lazy {
        getString(R.string.navigation_book_bookmark)
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
            Log.d("asdf", "backStackEntryCount: ${supportFragmentManager.backStackEntryCount}")
            val itemId = menuItem.itemId
            return@setOnItemSelectedListener when (itemId) {
                R.id.menu_bottom_navigation_search -> {
                    replaceFragment(searchFragmentTag)
//                    val fragment = supportFragmentManager.findFragmentByTag(searchFragmentTag)
//                            ?: BookListSearchFragment.newInstance("")
                    supportFragmentManager.findFragmentByTag(searchFragmentTag)?.let { fragment ->
                        supportFragmentManager.commit {
                            replace(
                                R.id.main_fragment_container,
                                fragment,
                                searchFragmentTag
                            )
                            setReorderingAllowed(true)
                        }
                    } ?: run {
                        supportFragmentManager.commit {
                            replace(
                                R.id.main_fragment_container,
                                BookListSearchFragment.newInstance(""),
                                searchFragmentTag
                            )
                            setReorderingAllowed(true)
                            addToBackStack("replacement")
                        }
                    }
                    true
                }
                R.id.menu_bottom_navigation_bookmark -> {
                    replaceFragment(bookmarkFragmentTag)
//                    val fragment = supportFragmentManager.findFragmentByTag(bookmarkFragmentTag)
//                        ?: BookmarkFragment.newInstance()
                    supportFragmentManager.findFragmentByTag(bookmarkFragmentTag)?.let { fragment ->
                        supportFragmentManager.commit {
                            replace(
                                R.id.main_fragment_container,
                                fragment,
                                bookmarkFragmentTag
                            )
                            setReorderingAllowed(true)
                        }
                    } ?: run {
                        supportFragmentManager.commit {
                            replace(
                                R.id.main_fragment_container,
                                BookmarkFragment.newInstance(),
                                bookmarkFragmentTag
                            )
                            setReorderingAllowed(true)
                            addToBackStack("replacement")
                        }
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

    private fun replaceFragment(key: String) {

    }
}