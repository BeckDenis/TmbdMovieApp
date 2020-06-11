package com.denisbeck.tmdbmovieapp.screens.popular

import androidx.lifecycle.*
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularViewModel(get()) }
}

class PopularViewModel(private val weatherRepo: MoviesRepository) : ViewModel() {

    var page = 1

    private val _movies = MutableLiveData<Resource<Movies>>()
    val movies: LiveData<Resource<Movies>>
        get() = _movies

    fun getPopularMovies() =
        viewModelScope.launch(Dispatchers.Default) {
            _movies.postValue(Resource.loading(null))
            _movies.postValue(weatherRepo.getPopularMovies(page))
        }

}