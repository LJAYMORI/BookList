package com.woody.booklist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.woody.booklist.databinding.ActivityMainNavigationBinding
import com.woody.detail.navigation.BookDetailNavigationArguments
import com.woody.search.BookListSearchCallback

class MainNavigationActivity : AppCompatActivity(), BookListSearchCallback {
    private lateinit var binding: ActivityMainNavigationBinding
    private val navController: NavController
        get() {
            val navFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            return navFragment.navController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
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
        navController.navigate(
            resId = R.id.action_book_list_search_fragment_to_bookDetailFragment,
            args = bundleOf(
                "args_book_detail" to BookDetailNavigationArguments(
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