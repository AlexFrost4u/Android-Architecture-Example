package com.example.android_architecture_example.ui.detail

import android.view.View
import androidx.lifecycle.*
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.domain.UserFull
import com.example.android_architecture_example.repository.UserRepository
import com.example.android_architecture_example.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*
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
    val selectedUser: LiveData<UserFull>
        get() = _selectedUser

    // DataState to determine state of retrofit query
    private val _userFullDataState: MutableLiveData<DataState<UserFull>> = MutableLiveData()

    // Exposing state
    val userFullDataState: LiveData<DataState<UserFull>>
        get() = _userFullDataState

    // Show progress bar
    private val _progressBarIsVisible = MutableLiveData<Int>()
    val progressBarIsVisible: LiveData<Int>
        get() = _progressBarIsVisible

    // Show error text
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

    fun displayProgressBar(isDisplayed: Boolean) {
        if (isDisplayed) {
            _progressBarIsVisible.value = View.VISIBLE
        } else {
            _progressBarIsVisible.value = View.GONE
        }
    }

    private fun showErrorText() {
        _errorTextIsVisible.value = View.VISIBLE
    }

    fun displayError(message: String?) {
        _errorText.value = message ?: "Unknown error"
        showErrorText()
    }

    fun setData(user: UserFull) {
        _selectedUser.value = user
    }

    /*fun getUserFullName(): String {
        val user = selectedUser.value
        val sb = StringBuilder()
        return if (user.title.isEmpty()) {
            sb.append(user.firstName + " ").append(user.lastName).toString()
        } else {
            sb.append(user.title.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            } + ".").append(user.firstName + " ").append(user.lastName).toString()
        }
    }*/

}

sealed class DetailStateEvent {
    object GetUserFullEvents : DetailStateEvent()
    object None : DetailStateEvent()
}