package com.example.githubapp.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepoDetailsBinding
import com.example.githubapp.delegates.viewBinding
import com.example.githubapp.ext.toReadableDate

class RepoDetailsFragment : DialogFragment(R.layout.fragment_repo_details) {

    private val binding by viewBinding(FragmentRepoDetailsBinding::bind)
    private val args by navArgs<RepoDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            nameTv.text = args.name
            timeTv.text = getString(R.string.last_updated_s, args.lastUpdated?.toReadableDate())
            starsTv.text = getString(R.string.stars_d, args.starsCount)
            forksTv.text = getString(R.string.forks_d, args.forkCount)
            okBtn.setOnClickListener {
                dismiss()
            }
        }
    }
}