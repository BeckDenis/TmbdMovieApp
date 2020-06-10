package com.denisbeck.tmdbmovieapp.repository

import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.PopularMoviesApi
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.networking.ResponseHandler
import org.koin.dsl.module

val popularMoviesModule = module {
    factory { MoviesRepository(get(), get()) }
}

class MoviesRepository(private val popularMoviesApi: PopularMoviesApi, private val responseHandler: ResponseHandler) {
    suspend fun getWeather(page: Int): Resource<Movies> {
        return try {
            val response = popularMoviesApi.getPopularMovies(page)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}