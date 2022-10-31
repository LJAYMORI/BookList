package com.woody.ui.keyboard

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


fun Activity.hideKeyboard() {
    val (context, windowToken) = currentFocus?.run {
        context to windowToken
    } ?: return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}
