package com.example.android_architecture_example.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android_architecture_example.databinding.FragmentDetailBinding
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.domain.UserFull
import com.example.android_architecture_example.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.setStateEvent(DetailStateEvent.GetUserFullEvents)
        subscribeObservers()

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.userFullDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<UserFull> -> {
                    viewModel.displayProgressBar(false)
                    viewModel.setData(dataState.data)
                }
                is DataState.Error -> {
                    viewModel.displayProgressBar(false)
                    viewModel.displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    viewModel.displayProgressBar(true)
                }
            }

        })
    }
}