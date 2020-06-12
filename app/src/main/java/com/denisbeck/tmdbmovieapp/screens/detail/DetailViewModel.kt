package com.denisbeck.tmdbmovieapp.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository


class DetailViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val movieId = MutableLiveData(0)

    fun updateMovieId(id: Int) {
        movieId.value = id
    }

    val movie = movieId.switchMap { id ->
        liveData {
            emit(moviesRepository.getMovie(id))
        }
    }

    val credits = movieId.switchMap { id ->
        liveData {
            emit(moviesRepository.getCredits(id))
        }
    }

}