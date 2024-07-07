package com.example.mowheel

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun SearchMovie() {
    val context = LocalContext.current
    val imdbIdProvider = remember { ImdbIdProvider(context) }
    var movieData by remember { mutableStateOf<Movie?>(null) }
    var posterUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val textStyle = TextStyle(color = Color.White)
    val apiKey = "d06ee10a"

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        posterUrl?.let { url ->
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MovieButton(
                isLoading = isLoading,
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        val randomImdbId = imdbIdProvider.getRandomImdbId()
                        movieData = APIOperations.fetchMovieDetails(randomImdbId, apiKey)
                        posterUrl = movieData?.let { APIOperations.getMoviePosterUrl(randomImdbId, apiKey) }
                        isLoading = false
                    }
                }
            )

            movieData?.let { movie ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Title: ${movie.Title}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Year: ${movie.Year}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Rated: ${movie.Rated}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Released: ${movie.Released}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Runtime: ${movie.Runtime}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Genre: ${movie.Genre}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Director: ${movie.Director}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Writer: ${movie.Writer}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Actors: ${movie.Actors}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Plot: ${movie.Plot}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Language: ${movie.Language}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Country: ${movie.Country}", style = textStyle)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Awards: ${movie.Awards}", style = textStyle)
                    }
                }
            }
        }
    }
}
