package com.example.githubapp.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

class DefaultDispatcherProvider(
    override val main: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
) : DispatcherProvider