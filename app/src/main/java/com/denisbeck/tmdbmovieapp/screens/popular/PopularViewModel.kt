package com.denisbeck.tmdbmovieapp.screens.popular

import android.util.Log
import androidx.lifecycle.*
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import com.denisbeck.tmdbmovieapp.screens.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PopularViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    companion object {
        private val TAG = PopularViewModel::class.java.simpleName
    }

    private val genre = MutableLiveData<Int?>(null)

    fun updateGenres(genreId: Int) {
        if (genreId != chipCheckedId) {
            genre.value = if (genreId == 0) null else genreId
        }
    }

    val genres = liveData {
        emit(moviesRepository.getGenres())
    }

    val movies = genre.switchMap { genre ->
        liveData {
            emit(Resource.loading(null))
            emit(moviesRepository.getPopularMovies(page, genre))
        }
    }

    var page: Int? = null
    var chipCheckedId: Int = 0
}