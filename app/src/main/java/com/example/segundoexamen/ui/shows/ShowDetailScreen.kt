package com.example.segundoexamen.ui.shows

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.example.segundoexamen.data.detail.TVShowDetail
import com.example.segundoexamen.data.detail.TVShowDetailViewModel
import com.example.segundoexamen.network.NetworkState

@Composable
fun ShowDetailScreen(viewModel: TVShowDetailViewModel, showId: Int) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getShowDetail(showId)
    }

    when (state) {
        is NetworkState.Loading -> LoadingScreen()
        is NetworkState.Success<*> -> carta((state as NetworkState.Success<TVShowDetail>).data, viewModel)
        is NetworkState.Error -> ErrorScreen((state as NetworkState.Error).message)
        else -> {}
    }
}


@Composable
fun carta(showDetail: TVShowDetail, viewModel: TVShowDetailViewModel) {
    val context = LocalContext.current
    val showsInDatabase by viewModel.getAllShowsFromDatabase(context).collectAsState(initial = emptyList())

    val isInDatabase = showsInDatabase.any { it.id == showDetail.id }

    Card (
        modifier = Modifier.padding(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFECFC)
        ),
        //shape = RoundedCornerShape(8.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(60.dp)  // Alto de 100dp
                    .fillMaxWidth()
            ) {
                val activity = context as? Activity
                IconButton(onClick = { activity?.finish() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atras"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(300.dp)
                //.width(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                        .padding(15.dp)
                ) {
                    AsyncImage(
                        model = showDetail.image.medium,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(18.dp))
                    )
                    FloatingTextBox("${showDetail.rating.average ?: "N/A"}", offsetX = 0.dp, offsetY = 0.dp)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(text = showDetail.name, fontSize = 28.sp)
                    Text(text = "Genres: ${showDetail.genres.joinToString(", ")}", fontSize = 18.sp)
                    Text(text = "Premiered: ${showDetail.premiered ?: "N/A"}", fontSize = 18.sp)
                    Text(text = "Network: ${showDetail.network?.name ?: "N/A"}", fontSize = 18.sp)
                    Text(text = "Language: ${showDetail.language ?: "N/A"}", fontSize = 18.sp)

                    val context = LocalContext.current

                    if (!isInDatabase) {
                        FloatingActionButton(
                            onClick = {
                                //agregar a la base de datos ROOM
                                viewModel.insertShowToDatabase(context, showDetail)
                                Toast.makeText(
                                    context,
                                    "${showDetail.name} agregado a favoritos",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //viewModel.printAllShowsInDatabase(context)
                            },

                            shape = CircleShape,
                            modifier = Modifier
                                //.padding(16.dp)
                                .size(60.dp)
                                .offset(x = (70).dp, y = (35).dp),
                            containerColor = Color.Yellow
                        ) {
                            Icon(Icons.Filled.Star, "Favoritos")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp) // que sea una línea
                    .background(Color.Magenta)
            )

            Text(
                //conversor de html a string
                text = HtmlCompat.fromHtml(showDetail.summary ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState()),

                fontSize = 20.sp
            )
        }
    }
}