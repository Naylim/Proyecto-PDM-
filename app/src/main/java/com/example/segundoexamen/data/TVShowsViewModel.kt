package com.example.segundoexamen.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.segundoexamen.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TVShowsViewModel(private val repository: TVShowRepository) : ViewModel() {

    private val _state = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val state: StateFlow<NetworkState> get() = _state

    fun getShows() = viewModelScope.launch {
        _state.value = NetworkState.Loading
        try {
            val shows = repository.getShows()
            _state.value = NetworkState.Success(shows)
        } catch (e: Exception) {
            _state.value = NetworkState.Error(e.message)
        }
    }

    fun searchShows(query: String) = viewModelScope.launch {
        _state.value = NetworkState.Loading
        try {
            val shows = repository.searchShows(query)
            _state.value = NetworkState.Success(shows)
            shows.forEach { show -> println(show) } // búsqueda en consola
        } catch (e: Exception) {
            _state.value = NetworkState.Error(e.message)
        }
    }
}
