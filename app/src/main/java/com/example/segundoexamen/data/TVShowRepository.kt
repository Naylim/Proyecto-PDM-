package com.example.segundoexamen.data

import com.example.segundoexamen.data.detail.TVShowDetail
import com.example.segundoexamen.network.TVMazeApiService

class TVShowRepository(private val api: TVMazeApiService) {
    suspend fun getShows(): List<TVShow> {
        return api.getShows().take(50) //primeros 50 shows
    }

    suspend fun getShowDetail(showId: Int): TVShowDetail {
        return api.getShowDetail(showId)
    }

    suspend fun searchShows(query: String): List<TVShow> {
        return api.searchShows(query).map { it.show }
    }
}
