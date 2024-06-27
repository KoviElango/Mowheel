package com.example.mowheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mowheel.ui.theme.MowheelTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MowheelTheme {
                Scaffold(
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            RoundButton()
                        }
                    }
                )
            }
        }
    }
}

interface OmdbApi {
    @GET("/")
    fun getMovieDetails(
        @Query("i") imdbId: String = "tt3896198",
        @Query("apikey") apiKey: String = "d06ee10a"
    ): Call<Movie>
}

data class Movie(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
)

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://www.omdbapi.com/"

    val instance: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}

@Composable
fun RoundButton() {
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
        Button(
            onClick = {
                println("Button clicked!") // Add this line for debugging
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
            },
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Blue),
            contentPadding = PaddingValues(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Movie!", color = Color.White)
            }
        }

        movieData?.let { movie ->
            Spacer(modifier = Modifier.height(16.dp))
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

@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    MowheelTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            RoundButton()
        }
    }
}