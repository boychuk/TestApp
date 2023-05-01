package com.example.githubapp.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.delegates.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> T.launch(
    loading: MutableStateFlow<Boolean>? = null,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job where T : ViewModel, T : ErrorHandler {
    return viewModelScope.launch(context, start) {
        try {
            loading?.emit(true)
            block()
        } catch (exc: Throwable) {
            consumeException(exc)
        } finally {
            viewModelScope.launch {
                loading?.emit(false)
            }
        }
    }
}