package com.example.mowheel

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbApi {
    @GET("/")
    fun getMovieDetails(
        @Query("i") imdbId: String = "tt3896198",
        @Query("apikey") apiKey: String = "d06ee10a"
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