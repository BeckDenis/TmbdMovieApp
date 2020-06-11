package com.denisbeck.tmdbmovieapp.networking

import com.denisbeck.tmdbmovieapp.models.Genres
import com.denisbeck.tmdbmovieapp.models.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMoviesApi {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int?, @Query("with_genres") genre: Int?): Movies
}

interface GenresApi {
    @GET("3/genre/movie/list")
    suspend fun getGenres(): Genres
}