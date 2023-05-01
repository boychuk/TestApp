package com.example.githubapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.databinding.ItemRepoBinding
import com.example.githubapp.model.response.UserRepoResponse

class RepoAdapter(
    private val onRepoClicked: (UserRepoResponse) -> Unit
): ListAdapter<UserRepoResponse, RepoAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onRepoClicked(currentList[adapterPosition])
            }
        }

        fun onBind(repo: UserRepoResponse) {
            with(binding) {
                nameTv.text = repo.name
                descriptionTv.text = repo.description
                descriptionTv.isVisible = !repo.description.isNullOrEmpty()
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<UserRepoResponse>() {
        override fun areItemsTheSame(oldItem: UserRepoResponse, newItem: UserRepoResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserRepoResponse, newItem: UserRepoResponse): Boolean {
            return oldItem == newItem
        }
    }
}