package com.denisbeck.tmdbmovieapp.repository

import android.util.Log
import com.denisbeck.tmdbmovieapp.models.Credits
import com.denisbeck.tmdbmovieapp.models.Genres
import com.denisbeck.tmdbmovieapp.models.Movie
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.*

class MoviesRepository(
    private val popularMoviesApi: PopularMoviesApi,
    private val responseHandler: ResponseHandler,
    private val genresApi: GenresApi,
    private val movieApi: MovieApi,
    private val creditsApi: CreditsApi
) {

    companion object {
        private val TAG = MoviesRepository::class.java.simpleName
    }

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

    suspend fun getMovie(id: Int): Resource<Movie> {
        return try {
            val response = movieApi.getMovie(id)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getCredits(id: Int): Resource<Credits> {
        return try {
            val response = creditsApi.getCredits(id)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.d(TAG, "getCredits: ${e.message}")
            responseHandler.handleException(e)
        }
    }

}