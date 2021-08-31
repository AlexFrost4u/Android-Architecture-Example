package com.example.android_architecture_example.ui.overview

import android.view.View
import androidx.lifecycle.*
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.repository.UserRepository
import com.example.android_architecture_example.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    // DataState to determine state of retrofit query
    private val _userDataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()

    // Exposing for global usage
    val userDataState: LiveData<DataState<List<User>>>
        get() = _userDataState

    // Data itself
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    // Navigate to selected User Profile
    private val _navigateToSelectedUserProfile = MutableLiveData<User?>()
    val navigateToSelectedUserProfile: LiveData<User?>
        get() = _navigateToSelectedUserProfile

    // Show progress bar
    private val _progressIsVisible = MutableLiveData<Int>()
    val progressBar: LiveData<Int>
        get() = _progressIsVisible

    // Show error text
    private val _errorTextIsVisible: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorTextIsVisible: LiveData<Int>
        get() = _errorTextIsVisible

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    private val _recyclerViewVisible: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val recyclerViewVisible: LiveData<Int>
        get() =_recyclerViewVisible

    fun setStateEvent(overviewStateEvent: OverviewStateEvent) {
        viewModelScope.launch {
            when (overviewStateEvent) {
                is OverviewStateEvent.GetUserEvents -> {
                    userRepository.getUser()
                        .onEach { dataState ->
                            _userDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is OverviewStateEvent.None -> Unit
            }
        }
    }

    fun showUI(users: List<User>){
        displayProgressBar(false)
        showErrorText(false)
        displayRecyclerView(true)

        _users.value = users
    }

    fun showError(message: String?){
        displayProgressBar(false)
        showErrorText(true)
        displayRecyclerView(false)

        _errorText.value = message ?: "Unknown error"
    }

    fun showLoading(){
        displayProgressBar(true)
        showErrorText(false)
        displayRecyclerView(false)
    }

    fun navigateToDetailScreen(user: User) {
        _navigateToSelectedUserProfile.value = user
    }

    fun doneNavigatingToDetailScreen() {
        _navigateToSelectedUserProfile.value = null
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        if (isDisplayed) {
            _progressIsVisible.value = View.VISIBLE
        } else {
            _progressIsVisible.value = View.GONE
        }
    }

    private fun showErrorText(isDisplayed: Boolean) {
        if (isDisplayed) {
            _errorTextIsVisible.value = View.VISIBLE
        } else {
            _errorTextIsVisible.value = View.GONE
        }
    }

    private fun displayRecyclerView(isDisplayed: Boolean){
        if (isDisplayed) {
            _recyclerViewVisible.value = View.VISIBLE
        } else {
            _recyclerViewVisible.value = View.GONE
        }
    }
}

sealed class OverviewStateEvent {
    object GetUserEvents : OverviewStateEvent()
    object None : OverviewStateEvent()
}