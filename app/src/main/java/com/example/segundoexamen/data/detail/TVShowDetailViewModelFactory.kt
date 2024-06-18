package com.example.segundoexamen.data.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.segundoexamen.data.TVShowRepository


class TVShowDetailViewModelFactory(private val repository: TVShowRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TVShowDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TVShowDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}