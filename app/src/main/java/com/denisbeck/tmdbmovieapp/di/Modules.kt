package com.denisbeck.tmdbmovieapp.di

import com.denisbeck.tmdbmovieapp.networking.*
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import com.denisbeck.tmdbmovieapp.screens.base.SharedViewModel
import com.denisbeck.tmdbmovieapp.screens.detail.DetailViewModel
import com.denisbeck.tmdbmovieapp.screens.popular.PopularViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    factory { MoviesRepository(get(), get(), get(), get(), get()) }
}

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { providePopularMoviesApi(get()) }
    factory { provideGenresApi(get()) }
    factory { provideMovieApi(get()) }
    factory { provideCreditsApi(get()) }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
}

val viewModelModule = module {
    viewModel { PopularViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SharedViewModel() }
}