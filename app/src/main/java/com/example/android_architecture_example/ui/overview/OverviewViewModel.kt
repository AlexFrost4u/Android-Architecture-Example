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
class OverviewViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // DataState to determine state of retrofit inquiry
    private val _dataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()

    // Exposing for global usage
    val dataState: LiveData<DataState<List<User>>>
        get() = _dataState

    private val _progressBarIsVisible = MutableLiveData<Int>()
    val progressBarIsVisible: LiveData<Int>
        get() = _progressBarIsVisible

    private val _mainText = MutableLiveData<String>()
    val mainText: LiveData<String>
        get() = _mainText

    fun setStateEvent(overviewStateEvent: OverviewStateEvent) {
        viewModelScope.launch {
            when (overviewStateEvent) {
                is OverviewStateEvent.GetUserEvents -> {
                    userRepository.getUser()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is OverviewStateEvent.None -> Unit
            }
        }
    }

    fun appendUserNames(users: List<User>) {
        val sb = StringBuilder()
        for (user in users) {
            sb.append(user.firstName + "\n")
        }
        _mainText.value = sb.toString()
    }

    fun displayProgressBar(isDisplayed: Boolean) {
        if(isDisplayed){
            _progressBarIsVisible.value = View.VISIBLE
        }else{
            _progressBarIsVisible.value = View.GONE
        }
    }

    fun displayError(message:String?){
        _mainText.value = message ?: "Unknown error"
    }
}

sealed class OverviewStateEvent {
    object GetUserEvents : OverviewStateEvent()

    object None : OverviewStateEvent()
}