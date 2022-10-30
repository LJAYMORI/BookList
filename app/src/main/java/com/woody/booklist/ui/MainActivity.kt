package com.woody.booklist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.woody.booklist.R
import com.woody.booklist.databinding.ActivityMainBinding
import com.woody.detail.BookDetailActivity
import com.woody.detail.navigation.BookDetailFragment
import com.woody.search.BookListSearchCallback

class MainActivity : AppCompatActivity(), BookListSearchCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val navController: NavController
        get() {
            val navFragment = supportFragmentManager
                .findFragmentById(R.id.main_fragment_container) as NavHostFragment
            return navFragment.navController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        initView()
    }

    private fun initView() {
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_bookListSearchFragment, R.id.navigation_bookmarkFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()
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