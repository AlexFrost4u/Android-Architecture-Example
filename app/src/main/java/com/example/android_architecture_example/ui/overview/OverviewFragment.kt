package com.example.android_architecture_example.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android_architecture_example.databinding.FragmentOverviewBinding
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.setStateEvent(OverviewStateEvent.GetUserEvents)

        subscribeObservers()
        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataSate ->
            when (dataSate) {
                is DataState.Success<List<User>> -> {
                    viewModel.displayProgressBar(false)
                    viewModel.appendUserNames(dataSate.data)
                }
                is DataState.Error -> {
                    viewModel.displayProgressBar(false)
                    viewModel.displayError(dataSate.exception.message)
                }
                is DataState.Loading -> {
                    viewModel.displayProgressBar(true)
                }
            }

        })
    }
}