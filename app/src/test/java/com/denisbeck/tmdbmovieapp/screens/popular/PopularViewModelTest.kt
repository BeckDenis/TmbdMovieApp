package com.denisbeck.tmdbmovieapp.screens.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.repository.MoviesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PopularViewModelTest {

    private lateinit var viewModel: PopularViewModel
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var moviesObserver: Observer<Resource<Movies>>
    private val invalidPage = -1
    private val validPage = 1

    private val errorResource = Resource.error("Unauthorised", null)
    private val successResource = Resource.success(Movies(listOf()))

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        moviesRepository = mock()
        runBlocking {
            whenever(moviesRepository.getPopularMovies(validPage)).thenReturn(successResource)
            whenever(moviesRepository.getPopularMovies(invalidPage)).thenReturn(errorResource)
        }
        viewModel = PopularViewModel(moviesRepository)
        moviesObserver = mock()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when getPopularMovies is called with valid page, then observer is updated with success`() = runBlocking {
        viewModel.movies.observeForever(moviesObserver)
        viewModel.page = validPage
        viewModel.getPopularMovies()
        delay(10)
        verify(moviesRepository).getPopularMovies(validPage)
        verify(moviesObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(moviesObserver, timeout(50)).onChanged(successResource)
    }

    @Test
    fun `when getPopularMovies is called with invalid page, then observer is updated with failure`() = runBlocking {
        viewModel.movies.observeForever(moviesObserver)
        viewModel.page = invalidPage
        viewModel.getPopularMovies()
        delay(10)
        verify(moviesRepository).getPopularMovies(invalidPage)
        verify(moviesObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(moviesObserver, timeout(50)).onChanged(errorResource)
    }

}