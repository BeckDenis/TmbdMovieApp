package com.denisbeck.tmdbmovieapp.networking

import com.denisbeck.tmdbmovieapp.models.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMoviesApi {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Movies
}