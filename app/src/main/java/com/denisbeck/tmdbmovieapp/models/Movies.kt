package com.denisbeck.tmdbmovieapp.models

data class Movies(val results: List<Movie>)

data class Movie(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val runtime: Int,
    val budget: Int,
    val genres: List<Genre>
)