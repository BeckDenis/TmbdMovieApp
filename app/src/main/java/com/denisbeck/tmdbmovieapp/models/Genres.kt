package com.denisbeck.tmdbmovieapp.models

data class Genres(val genres: List<Genre>)

data class Genre(
    val id: Int,
    val name: String
)