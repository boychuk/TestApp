package com.example.githubapp.api

import com.example.githubapp.model.response.UserRepoResponse
import com.example.githubapp.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/{name}")
    suspend fun searchUser(@Path("name") name: String): UserResponse

    @GET("users/{name}/repos")
    suspend fun userRepos(@Path("name") name: String): List<UserRepoResponse>
}