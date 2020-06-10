package com.denisbeck.tmdbmovieapp.screens.popular

import androidx.lifecycle.*
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ExampleViewModel(get()) }
}

class ExampleViewModel(private val weatherRepo: MoviesRepository) : ViewModel() {

    private var page = 1

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies>
        get() = _movies

    fun getPopularMovies() =
        viewModelScope.launch(Dispatchers.Default) {
            _movies.postValue(weatherRepo.getWeather(page))
        }

}