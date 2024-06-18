package com.example.segundoexamen.ui.shows

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.segundoexamen.data.detail.TVShowDetailViewModel
import com.example.segundoexamen.database.entity.Show
import kotlinx.coroutines.flow.map

    @Composable
    fun FavoriteScreen(viewModel: TVShowDetailViewModel) {
        // Observa el estado del ViewModel para la lista de shows
        val showsFavoriteState = viewModel.getAllShowsFromDatabase(context = LocalContext.current)
            .map { it }
            .collectAsState(initial = emptyList())
        val shows = showsFavoriteState.value

        if(shows.isEmpty()){ //si la lista de la database esta vacia, no hay favoritos
            noHay()
        }else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)  // Alto
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ){
                        val context = LocalContext.current
                        val activity = context as? Activity
                        IconButton(
                            modifier = Modifier.padding(25.dp),
                            onClick = { activity?.finish() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Atras"
                            )
                        }
                        Text(text = "FAVORITOS", fontSize = 30.sp, modifier = Modifier
                            .padding(top = 25.dp)
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(shows) { show ->
                        favoriteCard(show, viewModel)
                    }
                }
            }
        }
    }


    @Composable
    fun favoriteCard(show: Show, viewModel: TVShowDetailViewModel) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, ShowDetailActivity::class.java)
                    intent.putExtra("showId", show.id)
                    context.startActivity(intent)
                },
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
                val painter = rememberAsyncImagePainter(model = show.image)
                Box(
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .height(240.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    FloatingTextBox("${show.rating}", offsetX = 0.dp, offsetY = 0.dp)

                    FloatingActionButton(
                        onClick = {
                            viewModel.deleteShowFromDatabase(context, show)
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .size(60.dp)
                            .offset(x = 85.dp, y = 160.dp),
                        containerColor = Color.Red
                    ) {
                        Icon(Icons.Filled.Delete, "Eliminar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = show.genres,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }


    @Composable
    fun noHay() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    val context = LocalContext.current
                    val activity = context as? Activity
                    IconButton(
                        modifier = Modifier.padding(25.dp),
                        onClick = { activity?.finish() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                    Text(
                        text = "FAVORITOS", fontSize = 30.sp, modifier = Modifier
                            .padding(top = 25.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se han agregado favoritos :C",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
