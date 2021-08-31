package com.example.android_architecture_example.ui.detail

import android.view.View
import androidx.lifecycle.*
import com.example.android_architecture_example.domain.UserFull
import com.example.android_architecture_example.repository.UserRepository
import com.example.android_architecture_example.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    // Id of user that this screen will be showing
    private val _selectedUserId = MutableLiveData<String>()

    // User that we need to show
    private val _selectedUser = MutableLiveData<UserFull>()
    val selectedUserFull: LiveData<UserFull>
        get() = _selectedUser

    //
    val userFullName: LiveData<String> = Transformations.map(_selectedUser) {
        return@map (_selectedUser.value?.title + "."
                + _selectedUser.value?.firstName + " " + _selectedUser.value?.lastName)
    }

    // DataState to determine state of retrofit query
    private val _userFullDataState: MutableLiveData<DataState<UserFull>> = MutableLiveData()

    // Exposing state
    val userFullDataState: LiveData<DataState<UserFull>>
        get() = _userFullDataState

    // Visibility of UI elements
    private val _uiIsVisible = MutableLiveData<Int>()
    val uiIsVisible: LiveData<Int>
        get() = _uiIsVisible

    // Visibility of progress components
    private val _progressIsVisible = MutableLiveData<Int>()
    val progressIsVisible: LiveData<Int>
        get() = _progressIsVisible

    // Visibility of error components
    private val _errorTextIsVisible: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorTextIsVisible: LiveData<Int>
        get() = _errorTextIsVisible

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    init {
        _selectedUserId.value = savedStateHandle.get<String>("userId")
    }

    fun setStateEvent(detailStateEvent: DetailStateEvent) {
        viewModelScope.launch {
            when (detailStateEvent) {
                is DetailStateEvent.GetUserFullEvents -> {
                    userRepository.getUserFull(_selectedUserId.value!!)
                        .onEach { dataState ->
                            _userFullDataState.value = dataState
                            println(dataState)
                        }
                        .launchIn(viewModelScope)

                }
                is DetailStateEvent.None -> Unit
            }
        }
    }

    fun displayUI(user: UserFull) {
        showProgress(false)
        showErrorText(false)
        showUIElements(true)

        _selectedUser.value = user

    }

    fun displayLoading() {
        showProgress(true)
        showErrorText(false)
        showUIElements(false)
    }

    fun displayError(message: String?) {
        showProgress(false)
        showErrorText(true)
        showUIElements(false)

        _errorText.value = message ?: "Unknown error"
    }

    private fun showProgress(isDisplayed: Boolean) {
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

    private fun showUIElements(isDisplayed: Boolean) {
        if (isDisplayed) {
            _uiIsVisible.value = View.VISIBLE
        } else {
            _uiIsVisible.value = View.GONE
        }
    }
}

sealed class DetailStateEvent {
    object GetUserFullEvents : DetailStateEvent()
    object None : DetailStateEvent()
}