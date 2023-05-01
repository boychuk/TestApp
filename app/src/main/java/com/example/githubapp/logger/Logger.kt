package com.example.githubapp.logger

import android.util.Log

class Logger {

    fun logE(tag: String, throwable: Throwable) {
        Log.e(tag, "error", throwable)
    }
}