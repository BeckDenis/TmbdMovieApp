package com.denisbeck.tmdbmovieapp.screens.popular

import androidx.lifecycle.*
import com.denisbeck.tmdbmovieapp.models.Genre
import com.denisbeck.tmdbmovieapp.models.Genres
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import com.denisbeck.tmdbmovieapp.screens.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

class PopularViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val genre = MutableLiveData<Int?>(null)

    fun updateGenres(genreId: Int) {
        genre.value = genreId
    }

    var page: Int? = null

    val movies = genre.switchMap { genre ->
        liveData {
            emit(Resource.loading(null))
            emit(moviesRepository.getPopularMovies(page, genre))
        }
    }

    val genres = liveData {
        emit(moviesRepository.getGenres())
    }

}