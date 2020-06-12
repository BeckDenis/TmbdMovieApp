package com.denisbeck.tmdbmovieapp.networking

import com.denisbeck.tmdbmovieapp.models.Credits
import com.denisbeck.tmdbmovieapp.models.Genres
import com.denisbeck.tmdbmovieapp.models.Movie
import com.denisbeck.tmdbmovieapp.models.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PopularMoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int?, @Query("with_genres") genre: Int?): Movies
}

interface GenresApi {
    @GET("genre/movie/list")
    suspend fun getGenres(): Genres
}

interface MovieApi {
    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): Movie
}

interface CreditsApi {
    @GET("movie/{id}/credits")
    suspend fun getCredits(@Path("id") id: Int): Credits
}