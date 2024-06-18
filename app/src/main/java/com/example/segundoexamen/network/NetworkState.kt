package com.example.segundoexamen.network

sealed class NetworkState {
    object Idle : NetworkState()
    object Loading : NetworkState()
    data class Success<T>(val data: T) : NetworkState()
    data class Error(val message: String?) : NetworkState()
}

