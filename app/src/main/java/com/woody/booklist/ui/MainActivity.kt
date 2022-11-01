package com.woody.booklist.ui

import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.woody.booklist.R
import com.woody.booklist.databinding.ActivityMainBinding
import com.woody.bookmark.BookmarkCallback
import com.woody.bookmark.ui.BookmarkFragment
import com.woody.detail.ui.BookDetailActivity
import com.woody.search.BookListSearchCallback
import com.woody.search.ui.BookListSearchFragment
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData

class MainActivity : AppCompatActivity(), BookListSearchCallback, BookmarkCallback {

    companion object {
        private const val KEY_SAVED_STATE_CONTAINER = "key_saved_state_container"
        private const val KEY_SAVED_STATE_CURRENT_TAB = "key_saved_state_current_tab"

        private const val KEY_FRAGMENT_SEARCH_LIST = "key_fragment_search_list"
        private const val KEY_FRAGMENT_BOOKMARK = "key_fragment_bookmark"
    }

    private lateinit var binding: ActivityMainBinding

    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.navigation_bookListSearchFragment

    private val onNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
            return@OnItemSelectedListener when (val itemId = item.itemId) {
                R.id.navigation_bookListSearchFragment -> {
                    swapFragment(itemId, KEY_FRAGMENT_SEARCH_LIST)
                    true
                }
                R.id.navigation_bookmarkFragment -> {
                    swapFragment(itemId, KEY_FRAGMENT_BOOKMARK)
                    true
                }
                else -> {
                    false
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState == null) {
            swapFragment(R.id.navigation_bookmarkFragment, KEY_FRAGMENT_SEARCH_LIST)
        } else {
            savedStateSparseArray = savedInstanceState.getSparseParcelableArray(KEY_SAVED_STATE_CONTAINER)
                ?: savedStateSparseArray
            currentSelectItemId = savedInstanceState.getInt(KEY_SAVED_STATE_CURRENT_TAB)
        }
        binding.bottomNavigation.setOnItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSparseParcelableArray(KEY_SAVED_STATE_CONTAINER, savedStateSparseArray)
        outState.putInt(KEY_SAVED_STATE_CURRENT_TAB, currentSelectItemId)
    }

    override fun onClickBookItem(viewData: BookListViewHolderData) {
        startBookDetailActivity(viewData.toViewData())
    }

    override fun onClickedBookmarkItem(viewData: BookListViewHolderData) {
        startBookDetailActivity(viewData.toViewData())
    }

    private fun swapFragment(@IdRes actionId: Int, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            savedFragmentState(actionId)
            createFragment(actionId, tag)
        }
    }

    private fun createFragment(actionId: Int, tag: String) {
        val fragment = when (tag) {
            KEY_FRAGMENT_SEARCH_LIST -> {
                BookListSearchFragment.newInstance()
            }
            KEY_FRAGMENT_BOOKMARK -> {
                BookmarkFragment.newInstance()
            }
            else -> return
        }
        fragment.setInitialSavedState(savedStateSparseArray[actionId])
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment, tag)
            .commit()
    }

    private fun savedFragmentState(actionId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        if (currentFragment != null) {
            savedStateSparseArray.put(
                currentSelectItemId,
                supportFragmentManager.saveFragmentInstanceState(currentFragment)
            )
        }
        currentSelectItemId = actionId
    }

    private fun startBookDetailActivity(viewData: BookListViewHolderData) {
        startActivity(BookDetailActivity.newIntent(this, viewData))
    }

    private fun BookListViewHolderData.toViewData(): BookListViewHolderData {
        return BookListViewHolderData(
            title = title,
            author = author,
            isbn = isbn,
            price = price,
            image = image,
            publisher = publisher,
            pubdate = pubdate,
            discount = discount,
            description = description,
            isBookmarked = isBookmarked,
        )
    }
}