package com.woody.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.woody.detail.R
import com.woody.ui.recyclerview.viewholder.data.BookListViewHolderData

class BookDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_VIEW_DATA = "extra_view_data"

        fun newIntent(context: Context, viewData: BookListViewHolderData): Intent {
            return Intent(context, BookDetailActivity::class.java).apply {
                putExtra(EXTRA_VIEW_DATA, viewData)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        intent?.getParcelableExtra<BookListViewHolderData>(EXTRA_VIEW_DATA)?.let { viewData ->
            supportFragmentManager.commit(true) {
                replace(R.id.book_detail_fragment_container, BookDetailFragment.newInstance(viewData))
            }
        } ?: run {
            Toast.makeText(this, getString(R.string.detail_invalid_arguments), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}