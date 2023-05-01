package com.example.githubapp

import android.content.Context
import app.cash.turbine.test
import com.example.githubapp.delegates.DefaultErrorHandler
import com.example.githubapp.dispatchers.DefaultDispatcherProvider
import com.example.githubapp.logger.Logger
import com.example.githubapp.model.request.UserReposRequest
import com.example.githubapp.model.request.UserRequest
import com.example.githubapp.model.response.UserRepoResponse
import com.example.githubapp.model.response.UserResponse
import com.example.githubapp.repository.UserRepository
import com.example.githubapp.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userMock: UserResponse

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var reposMock: List<UserRepoResponse>

    private lateinit var viewModel: SearchViewModel

    @Before
    fun before() {
        viewModel = SearchViewModel(
            userRepository = userRepository,
            errorHandler = DefaultErrorHandler(context, logger),
            dispatcherProvider = DefaultDispatcherProvider(
                main = mainDispatcherRule.testDispatcher,
                io = mainDispatcherRule.testDispatcher,
                default = mainDispatcherRule.testDispatcher,
            )
        )
    }

    @Test
    fun `get user data`() = runTest {
        val userReq = UserRequest("name")
        val reposReq = UserReposRequest("name")

        Mockito.`when`(userRepository.searchUser(userReq)).thenReturn(userMock)
        Mockito.`when`(userRepository.userRepos(reposReq)).thenReturn(reposMock)

        launch {
            viewModel.search("name")
        }
        viewModel.user.test {
            Assert.assertEquals(null, awaitItem())
            Assert.assertEquals(userMock, awaitItem())
        }
    }

    @Test
    fun `get user repos`() = runTest {
        val userReq = UserRequest("name")
        val reposReq = UserReposRequest("name")

        Mockito.`when`(userRepository.searchUser(userReq)).thenReturn(userMock)
        Mockito.`when`(userRepository.userRepos(reposReq)).thenReturn(reposMock)

        launch {
            viewModel.search("name")
        }

        viewModel.userRepoList.test {
            Assert.assertEquals(null, awaitItem())
            Assert.assertEquals(reposMock, awaitItem())
        }
    }

    @Test
    fun loading() = runTest {
        val userReq = UserRequest("name")
        val reposReq = UserReposRequest("name")

        Mockito.`when`(userRepository.searchUser(userReq)).thenReturn(userMock)
        Mockito.`when`(userRepository.userRepos(reposReq)).thenReturn(reposMock)

        launch {
            viewModel.search("name")
        }

        viewModel.isLoading.test {
            Assert.assertEquals(false, awaitItem())
            Assert.assertEquals(true, awaitItem())
            Assert.assertEquals(false, awaitItem())
        }
    }

    @Test
    fun error() = runTest {
        val userReq = UserRequest("name")
        val reposReq = UserReposRequest("name")

        val error = IllegalStateException("test")
        Mockito.`when`(userRepository.searchUser(userReq)).thenThrow(error)
        Mockito.`when`(userRepository.userRepos(reposReq)).thenThrow(error)

        launch {
            viewModel.search("name")
        }

        viewModel.errorEvent.test {
            Assert.assertEquals("test", awaitItem())
        }
    }
}