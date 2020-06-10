package com.denisbeck.tmdbmovieapp

import android.app.Application
import com.denisbeck.tmdbmovieapp.networking.networkModule
import com.denisbeck.tmdbmovieapp.repository.popularMoviesModule
import com.denisbeck.tmdbmovieapp.screens.popular.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TmdbMovie : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TmdbMovie)
            modules(listOf(viewModelModule, networkModule, popularMoviesModule))
        }
    }
}