package com.example.githubapp.di

import com.example.githubapp.api.UserApi
import com.example.githubapp.repository.UserRepository
import com.example.githubapp.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun userRepository(api: UserApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}