package com.denisbeck.tmdbmovieapp.repository

import com.denisbeck.tmdbmovieapp.models.Genres
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.GenresApi
import com.denisbeck.tmdbmovieapp.networking.PopularMoviesApi
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.networking.ResponseHandler
import org.koin.dsl.module

val popularMoviesModule = module {
    factory { MoviesRepository(get(), get(), get()) }
}

class MoviesRepository(
    private val popularMoviesApi: PopularMoviesApi,
    private val responseHandler: ResponseHandler,
    private val genresApi: GenresApi
) {

    suspend fun getPopularMovies(page: Int?, genre: Int?): Resource<Movies> {
        return try {
            val response = popularMoviesApi.getPopularMovies(page, genre)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getGenres(): Resource<Genres> {
        return try {
            val response = genresApi.getGenres()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}