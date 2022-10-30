package com.woody.util

import java.text.DecimalFormat
import java.util.Locale

fun String.toCurrency(): String {
    return try {
        DecimalFormat.getInstance(Locale.KOREAN).format(this.toInt())
    } catch (e: NumberFormatException) {
        this
    }
}