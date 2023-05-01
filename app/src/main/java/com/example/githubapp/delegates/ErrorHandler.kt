package com.example.githubapp.delegates

import android.content.Context
import com.example.githubapp.R
import com.example.githubapp.logger.Logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface ErrorHandler {

    val errorEvent: SharedFlow<String>

    suspend fun consumeException(throwable: Throwable)

    suspend fun updateError(message: String)
}

class DefaultErrorHandler(
    private val context: Context,
    private val logger: Logger
) : ErrorHandler {

    override val errorEvent = MutableSharedFlow<String>()

    override suspend fun consumeException(throwable: Throwable) {
        logger.logE("DefaultErrorHandler", throwable)
        updateError(throwable.localizedMessage ?: context.getString(R.string.error_unknown))
    }

    override suspend fun updateError(message: String) {
        errorEvent.emit(message)
    }
}