package com.example.segundoexamen.ui.shows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.segundoexamen.data.TVShowRepository
import com.example.segundoexamen.data.detail.TVShowDetailViewModel
import com.example.segundoexamen.data.detail.TVShowDetailViewModelFactory
import com.example.segundoexamen.network.TVMazeApiService
import com.example.segundoexamen.network.provideRetrofit

class ShowDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val showId = intent.getIntExtra("showId", -1)
            val viewModel: TVShowDetailViewModel = ViewModelProvider(
                this,
                TVShowDetailViewModelFactory(TVShowRepository(provideRetrofit().create(TVMazeApiService::class.java)))
            )[TVShowDetailViewModel::class.java]

            ShowDetailScreen(viewModel, showId)
        }
    }
}