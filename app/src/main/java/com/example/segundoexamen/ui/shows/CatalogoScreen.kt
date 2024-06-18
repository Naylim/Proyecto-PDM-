package com.example.segundoexamen.ui.shows

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.segundoexamen.data.TVShow
import com.example.segundoexamen.data.TVShowsViewModel
import com.example.segundoexamen.network.NetworkState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVShowApp(viewModel: TVShowsViewModel, onShowClick: (Int) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            header(viewModel)
        }
    ) { innerPadding ->
        val state by viewModel.state.collectAsState()

        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is NetworkState.Loading -> LoadingScreen()
                is NetworkState.Success<*> -> ShowGrid((state as NetworkState.Success<List<TVShow>>).data, onShowClick)
                is NetworkState.Error -> ErrorScreen((state as NetworkState.Error).message)
                else -> {}
            }

            LaunchedEffect(Unit) {
                viewModel.getShows()
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowGrid(shows: List<TVShow>, onShowClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(shows) { show ->
            ShowCard(show) {
                onShowClick(show.id)
            }
        }
    }
}

@Composable
fun ShowCard(show: TVShow, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFECFC)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                text = show.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            val painter = rememberAsyncImagePainter(model = show.image.medium)
            Box(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            ){
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                FloatingTextBox("${show.rating.average ?: "N/A"}", offsetX = 0.dp, offsetY = 0.dp)

            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = show.genres.joinToString(", "),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
@Composable
fun FloatingTextBox(
    text: String,
    modifier: Modifier = Modifier,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
) {
    Box(
        modifier = modifier
            .offset(x = offsetX, y = offsetY) // Coordenadas X e Y
            .padding(10.dp)
            .background(color = Color.Magenta.copy(alpha = 0.5f))
            .clip(RoundedCornerShape(16.dp))
            //.padding(10.dp)
            .height(20.dp)
            .width(28.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ErrorScreen(message: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message ?: "Unknown error")
    }
}

@Composable
fun header(viewModel: TVShowsViewModel){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
            //.background(Color.Cyan),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){

                Text(text = "CATALOGO TV Maze", fontSize = 30.sp, modifier = Modifier
                    .padding(20.dp)
                    .padding(top = 10.dp))
                val context = LocalContext.current
                IconButton(
                    onClick = {
                        val intent = Intent(context, FavoriteActivity::class.java)
                        context.startActivity(intent)
                        },
                    modifier = Modifier
                        .size(65.dp)
                        .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        modifier = Modifier.size(65.dp),
                        contentDescription = "Favorite icon",
                        tint = Color.Magenta
                    )
                }
            }
            search(viewModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(viewModel: TVShowsViewModel){  //barra de busqueda
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                //.width(270.dp)
                .height(70.dp)
                .padding(horizontal = 18.dp),
                //.background(Color(0xFFFFE1FB)),
                query = text,
                onQueryChange = {
                    text = it
                },
                onSearch = {
                    //realiza busqueda
                    viewModel.searchShows(it)
                    active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = {
                    //cuando no hay texto
                    Text("Buscar")
                    viewModel.getShows()
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                },
                trailingIcon = {
                    if(active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if(text.isNotEmpty()){
                                    text = ""
                                    viewModel.getShows()
                                }else{
                                    active = false
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "close icon"
                        )
                    }
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color(0xFFFFECFC)
                    )
            ) {

            }

        }

    }
