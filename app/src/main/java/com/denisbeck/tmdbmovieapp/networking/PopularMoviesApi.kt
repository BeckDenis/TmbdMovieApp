package com.denisbeck.tmdbmovieapp.networking

import com.denisbeck.tmdbmovieapp.models.PopularMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMoviesApi {
    @GET("3/movie/popular")
    fun getPopularMovies(@Query("api_key") key: String, @Query("page") page: Int): Call<PopularMovies>
}