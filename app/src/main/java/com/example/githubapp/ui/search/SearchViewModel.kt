package com.example.githubapp.ui.search

import androidx.lifecycle.ViewModel
import com.example.githubapp.delegates.ErrorHandler
import com.example.githubapp.dispatchers.DispatcherProvider
import com.example.githubapp.ext.launch
import com.example.githubapp.model.request.UserReposRequest
import com.example.githubapp.model.request.UserRequest
import com.example.githubapp.model.response.UserRepoResponse
import com.example.githubapp.model.response.UserResponse
import com.example.githubapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val errorHandler: ErrorHandler,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), ErrorHandler by errorHandler {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user = _user.asStateFlow()

    private val _userRepoList = MutableStateFlow<List<UserRepoResponse>?>(null)
    val userRepoList = _userRepoList.asStateFlow()

    private var searchJob: Job? = null

    fun search(text: String) {
        searchJob?.cancel()
        searchJob = launch(loading = _isLoading) {
            val userDeferred = async(dispatcherProvider.default + SupervisorJob()) {
                userRepository.searchUser(UserRequest(name = text))
            }
            val userRepoListDeferred = async(dispatcherProvider.default + SupervisorJob()) {
                userRepository.userRepos(UserReposRequest(name = text))
            }

            val userResp = userDeferred.await()
            val userReposResp = userRepoListDeferred.await()

            _user.emit(userResp)
            _userRepoList.emit(userReposResp)
        }
    }
}