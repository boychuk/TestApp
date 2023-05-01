package com.example.githubapp.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentSearchBinding
import com.example.githubapp.delegates.autoCleaned
import com.example.githubapp.delegates.viewBinding
import com.example.githubapp.ext.collect
import com.example.githubapp.ext.navigate
import com.example.githubapp.ext.showMessage
import com.example.githubapp.ext.src
import com.example.githubapp.model.response.UserRepoResponse
import com.example.githubapp.model.response.UserResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private var adapter by autoCleaned { RepoAdapter(::navigateToDetails) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        collectStates()
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        searchBtn.setOnClickListener {
            search()
        }
        searchEt.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun collectStates() {
        collect(viewModel.user, ::setUserData)
        collect(viewModel.isLoading, ::setLoading)
        collect(viewModel.errorEvent, ::showMessage)
        collect(viewModel.userRepoList, adapter::submitList)
    }

    private fun search() {
        binding.searchEt.text?.toString()?.apply(viewModel::search)
    }

    private fun setUserData(user: UserResponse?) = with(binding) {
        avatarIv.src(user?.avatarUrl)
        nameTv.text = user?.name
    }

    private fun setLoading(isLoading: Boolean) = with(binding) {
        searchProgress.isVisible = isLoading
    }

    private fun navigateToDetails(repo: UserRepoResponse) {
        navigate(
            SearchFragmentDirections.actionSearchToRepoDetails(
                name = repo.fullName,
                lastUpdated = repo.updatedAt,
                starsCount = repo.stargazersCount ?: 0,
                forkCount = repo.forksCount ?: 0
            )
        )
    }
}