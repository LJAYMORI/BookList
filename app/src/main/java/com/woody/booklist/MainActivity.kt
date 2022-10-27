package com.woody.booklist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.woody.booklist.databinding.ActivityMainBinding
import com.woody.domain_detail.BookDetailFragment
import com.woody.domain_search.BookListSearchCallback

class MainActivity : AppCompatActivity(), BookListSearchCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navFragment.navController
//        navController.setGraph(R.navigation.navigation_main_graph)
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
        findNavController(R.id.nav_host_fragment).navigate(
            resId = R.id.action_book_list_search_to_book_detail,
            args = bundleOf(
                "args_book_detail_image" to BookDetailFragment.Argument(
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