package com.example.segundoexamen.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TVShowsViewModelFactory(private val repository: TVShowRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TVShowsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TVShowsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
