package com.denisbeck.tmdbmovieapp

import android.app.Application
import com.denisbeck.tmdbmovieapp.di.networkModule
import com.denisbeck.tmdbmovieapp.di.moviesModule
import com.denisbeck.tmdbmovieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TmdbMovie : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TmdbMovie)
            modules(listOf(viewModelModule, networkModule, moviesModule))
        }
    }
}