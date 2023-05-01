package com.example.githubapp.di

import android.content.Context
import com.example.githubapp.delegates.DefaultErrorHandler
import com.example.githubapp.delegates.ErrorHandler
import com.example.githubapp.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ErrorModule {

    @Provides
    fun provideErrorHandler(
        @ApplicationContext context: Context,
        logger: Logger
    ): ErrorHandler {
        return DefaultErrorHandler(context, logger)
    }
}