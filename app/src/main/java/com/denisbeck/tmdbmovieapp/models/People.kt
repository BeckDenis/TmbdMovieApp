package com.denisbeck.tmdbmovieapp.models

data class Credits(val cast: List<Actor>, val crew: List<Worker>) {

    fun credits() = mutableListOf<Credit>().apply {
        addAll(cast.map { Credit(name = it.name, caption = it.character, profile_path = it.profile_path) })
        addAll(crew.map { Credit(name = it.name, caption = it.job, profile_path = it.profile_path) })
    }
}

data class Actor(val character: String, val name: String, val profile_path: String?)

data class Worker(val job: String, val name: String, val profile_path: String?)

data class Credit(val caption: String, val name: String, val profile_path: String?)