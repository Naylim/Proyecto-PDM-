package com.example.segundoexamen.network

import com.example.segundoexamen.data.TVShow
import com.example.segundoexamen.data.detail.TVShowDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVMazeApiService {
    @GET("shows")
    suspend fun getShows(): List<TVShow>

    @GET("shows/{id}")
    suspend fun getShowDetail(@Path("id") showId: Int, @Query("embed") embed: String = "cast"): TVShowDetail

    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResult>
}

data class SearchResult( //resulados de la busqueda
    val show: TVShow
)