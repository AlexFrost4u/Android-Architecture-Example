package com.example.android_architecture_example.ui

import androidx.lifecycle.*
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.repository.MainRepository
import com.example.android_architecture_example.util.DataState
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    /*@Assisted*/ private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()

    val dataSate: LiveData<DataState<List<User>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetUserEvents -> {
                    mainRepository.getUser()
                        .onEach { dataSate ->
                            _dataState.value = dataSate
                        }
                        .launchIn(viewModelScope)
                }

            }
        }
    }

}

sealed class MainStateEvent {
    object GetUserEvents : MainStateEvent()

    object None : MainStateEvent()
}