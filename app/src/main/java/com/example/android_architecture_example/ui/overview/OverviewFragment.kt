package com.example.android_architecture_example.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        // Set up binding
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Get user list
        viewModel.setStateEvent(OverviewStateEvent.GetUserEvents)


        subscribeObservers()

        // Set up navigation upon click on recyclerview element
        binding.userGrid.adapter = UserGridAdapter(UserGridAdapter.OnClickListener {
            viewModel.apply {
                navigateToDetailScreen(it)
            }
        })

        // Navigate to chosen user with ID
        viewModel.navigateToSelectedUserProfile.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it.id))
                viewModel.doneNavigatingToDetailScreen()
            }
        })
        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.userDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<User>> -> {
                    viewModel.showUI(dataState.data)
                }
                is DataState.Error -> {
                    viewModel.showError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    viewModel.showLoading()
                }
            }

        })
    }
}