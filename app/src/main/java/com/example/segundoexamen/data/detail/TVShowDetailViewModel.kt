package com.example.segundoexamen.data.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.segundoexamen.data.TVShowRepository
import com.example.segundoexamen.database.AppDatabase
import com.example.segundoexamen.database.entity.Show
import com.example.segundoexamen.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class TVShowDetailViewModel(private val repository: TVShowRepository) : ViewModel() {
    // Estado del ViewModel utilizando MutableStateFlow
    private val _state = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val state: StateFlow<NetworkState> get() = _state

    // obtener los detalles de un show por su ID
    fun getShowDetail(showId: Int) = viewModelScope.launch {
        _state.value = NetworkState.Loading
        try {
            val showDetail = repository.getShowDetail(showId)
            _state.value = NetworkState.Success(showDetail)
        } catch (e: Exception) {
            _state.value = NetworkState.Error(e.message)
        }
    }

    // insertar un show en la base de datos
    fun insertShowToDatabase(context: Context, showDetail: TVShowDetail) = viewModelScope.launch {
        val showEntity = Show(
            id = showDetail.id,
            name = showDetail.name,
            image = showDetail.image.medium,
            rating = showDetail.rating.average.toString(),
            genres = showDetail.genres.joinToString(", "),
            startDate = showDetail.premiered.toString(),
            region = showDetail.network?.name.toString(),
            language = showDetail.language.toString(),
            description = showDetail.summary.toString()
        )
        AppDatabase.getDatabase(context).showDao().insertShow(showEntity)
    }

    // get a todos los shows almacenados en la base de datos como un flujo (Flow)
    fun getAllShowsFromDatabase(context: Context): Flow<List<Show>> {
        return AppDatabase.getDatabase(context).showDao().getAll()
    }

    // eliminar un show de la base de datos
    fun deleteShowFromDatabase(context: Context, show: Show) = viewModelScope.launch(Dispatchers.IO) {
        try {
            AppDatabase.getDatabase(context).showDao().delete(show)
        } catch (e: Exception) {
            Log.e("TVShowDetailViewModel", "Error deleting show from database", e)
        }
    }
}
