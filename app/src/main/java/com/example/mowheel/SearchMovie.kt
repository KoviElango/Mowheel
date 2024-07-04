package com.example.mowheel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun SearchMovie() {
    var movieData by remember { mutableStateOf<Movie?>(null) }
    var posterUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val textStyle = TextStyle(color = Color.Black)
    val imdbId = "tt3896198"
    val apiKey = "d06ee10a"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        posterUrl?.let { url ->
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        MovieButton(
            isLoading = isLoading,
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    movieData = APIOperations.fetchMovieDetails(imdbId, apiKey)
                    posterUrl = movieData?.let { APIOperations.getMoviePosterUrl(imdbId, apiKey) }
                    isLoading = false
                }
            }
        )

        movieData?.let { movie ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Title: ${movie.Title}", style = textStyle)
            Text("Year: ${movie.Year}", style = textStyle)
            Text("Rated: ${movie.Rated}", style = textStyle)
            Text("Released: ${movie.Released}", style = textStyle)
            Text("Runtime: ${movie.Runtime}", style = textStyle)
            Text("Genre: ${movie.Genre}", style = textStyle)
            Text("Director: ${movie.Director}", style = textStyle)
            Text("Writer: ${movie.Writer}", style = textStyle)
            Text("Actors: ${movie.Actors}", style = textStyle)
            Text("Plot: ${movie.Plot}", style = textStyle)
            Text("Language: ${movie.Language}", style = textStyle)
            Text("Country: ${movie.Country}", style = textStyle)
            Text("Awards: ${movie.Awards}", style = textStyle)
        }
    }
}
