package com.example.segundoexamen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.segundoexamen.data.TVShowRepository
import com.example.segundoexamen.data.TVShowsViewModel
import com.example.segundoexamen.data.TVShowsViewModelFactory
import com.example.segundoexamen.network.TVMazeApiService
import com.example.segundoexamen.network.provideRetrofit
import com.example.segundoexamen.ui.shows.ShowDetailActivity
import com.example.segundoexamen.ui.shows.TVShowApp

class MainActivity : ComponentActivity() {

    private val viewModel: TVShowsViewModel by viewModels {
        TVShowsViewModelFactory(TVShowRepository(provideRetrofit().create(TVMazeApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVShowApp(viewModel) { showId ->
                val intent = Intent(this, ShowDetailActivity::class.java)
                intent.putExtra("showId", showId)
                startActivity(intent)
            }
        }
    }
}

