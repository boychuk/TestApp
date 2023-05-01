package com.example.githubapp.repository

import com.example.githubapp.api.UserApi
import com.example.githubapp.model.request.UserReposRequest
import com.example.githubapp.model.request.UserRequest
import com.example.githubapp.model.response.UserRepoResponse
import com.example.githubapp.model.response.UserResponse

interface UserRepository {
    suspend fun searchUser(request: UserRequest): UserResponse
    suspend fun userRepos(request: UserReposRequest): List<UserRepoResponse>
}

class UserRepositoryImpl(
    private val userApi: UserApi
): UserRepository {

    override suspend fun searchUser(request: UserRequest): UserResponse {
        return userApi.searchUser(request.name)
    }

    override suspend fun userRepos(request: UserReposRequest): List<UserRepoResponse> {
        return userApi.userRepos(request.name)
    }
}