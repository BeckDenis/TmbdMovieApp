package com.denisbeck.tmdbmovieapp.repository

import com.denisbeck.tmdbmovieapp.networking.PopularMoviesApi
import org.koin.dsl.module

val popularMoviesModule = module {
    factory { MoviesRepository(get()) }
}

class MoviesRepository(private val popularMoviesApi: PopularMoviesApi) {
    suspend fun getWeather(page: Int) = popularMoviesApi.getPopularMovies(page)
}