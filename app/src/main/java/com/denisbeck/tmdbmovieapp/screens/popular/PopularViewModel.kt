package com.denisbeck.tmdbmovieapp.screens.popular

import androidx.lifecycle.*
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import com.denisbeck.tmdbmovieapp.screens.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

class PopularViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val genre = MutableLiveData<Int?>(null)

    fun updateGenres(genreId: Int) {
        genre.value = if (genreId == 0) null else genreId
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