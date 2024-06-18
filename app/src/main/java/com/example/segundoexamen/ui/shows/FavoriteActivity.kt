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
import com.example.segundoexamen.ui.theme.SegundoExamenTheme

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProvider(this).get(TVShowDetailViewModel::class.java)
        val viewModel: TVShowDetailViewModel = ViewModelProvider(
            this,
            TVShowDetailViewModelFactory(TVShowRepository(provideRetrofit().create(TVMazeApiService::class.java)))
        )[TVShowDetailViewModel::class.java]
        setContent {
            SegundoExamenTheme {
                // Pantalla de lista de favoritos
                FavoriteScreen(viewModel)
            }
        }
    }
}
