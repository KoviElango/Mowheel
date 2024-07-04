package com.example.mowheel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object APIOperations {

    private val apiService: OmdbApi by lazy {
        RetrofitClient.instance.create(OmdbApi::class.java)
    }

    suspend fun fetchMovieDetails(imdbId: String, apiKey: String): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovieDetails(imdbId, apiKey).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getMoviePosterUrl(imdbId: String, apiKey: String): String {
        return "https://img.omdbapi.com/?i=$imdbId&apikey=$apiKey"
    }
}

interface OmdbApi {
    @GET("/")
    fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String
    ): Call<Movie>
}

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
