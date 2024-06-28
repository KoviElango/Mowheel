package com.example.mowheel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SearchMovie() {
    val apiService = RetrofitClient.instance.create(OmdbApi::class.java)
    var movieData by remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val textStyle = TextStyle(color = Color.Black)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        MovieButton(
            isLoading = isLoading,
            onClick = {
                isLoading = true
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val response = apiService.getMovieDetails().execute()
                        if (response.isSuccessful) {
                            movieData = response.body()
                        } else {
                            println("Error: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Failure: ${e.message}")
                    } finally {
                        isLoading = false
                    }
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