package com.woody.booklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.woody.booklist.databinding.ActivityMainBinding
import com.woody.domain_detail.BookDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navFragment.navController
        navController.setGraph(R.navigation.navigation_main_graph)

        binding.testText.setOnClickListener {
            startActivity(Intent(this, BookDetailActivity::class.java))
        }
    }

}