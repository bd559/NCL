package com.example.ncl.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ncl.models.CruiseShip
import com.example.ncl.repository.CruiseShipRepository
import com.example.ncl.repository.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CruiseShipViewModel @Inject constructor(
    private val cruiseShipRepository: CruiseShipRepository
) : ViewModel() {

    private var _state = MutableLiveData<State>(State.Initial)
    val state: LiveData<State> get() = _state
    fun getCruiseShips(cruiseShip: String) {
        viewModelScope.launch {
            cruiseShipRepository.getCruiseShipInfo(cruiseShip).collect { state ->
                _state.value = when (state) {
                    is NetworkState.Loading -> State.Loading
                    is NetworkState.Success -> State.Success(state.data)
                    is NetworkState.Failure -> State.Failure
                }
            }
        }
    }
}

sealed class State {
    object Initial : State()
    object Loading : State()
    data class Success(val data: CruiseShip) : State()
    object Failure : State()
}