package com.example.githubapp.ext

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.toReadableDate(): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(this)
        val returnFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        returnFormat.format(date!!)
    } catch (exc: Exception) {
        Log.e("Parse error", "Error", exc)
        ""
    }
}