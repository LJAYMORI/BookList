package com.woody.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.woody.detail.navigation.BookDetailFragment

class BookDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ARGS = "extra_args"

        fun newIntent(context: Context, args: BookDetailFragment.Args): Intent {
            return Intent(context, BookDetailActivity::class.java).apply {
                putExtra(EXTRA_ARGS, args)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        intent?.getParcelableExtra<BookDetailFragment.Args>(EXTRA_ARGS)?.let { args ->
            supportFragmentManager.commit(true) {
                replace(R.id.book_detail_fragment_container, BookDetailFragment.newInstance(args))
            }
        } ?: run {
            Toast.makeText(this, getString(R.string.detail_invalid_arguments), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}