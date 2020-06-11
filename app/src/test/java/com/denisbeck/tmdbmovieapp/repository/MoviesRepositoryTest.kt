package com.denisbeck.tmdbmovieapp.repository

import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.PopularMoviesApi
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.networking.ResponseHandler
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.HttpException

class MoviesRepositoryTest {

    private val responseHandler = ResponseHandler()
    private lateinit var popularMoviesApi: PopularMoviesApi
    private lateinit var repository: MoviesRepository
    private val invalidPage = -1
    private val validPage = 1
    private val movies = Movies(listOf())
    private val moviesResponse = Resource.success(movies)
    private val errorResponse = Resource.error("Invalid parameters: Your request parameters are incorrect", null)


    @Before
    fun setUp() {
        popularMoviesApi = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(422)
        runBlocking {
            whenever(popularMoviesApi.getPopularMovies(eq(invalidPage))).thenThrow(mockException)
            whenever(popularMoviesApi.getPopularMovies(eq(validPage))).thenReturn(movies)
        }
        repository = MoviesRepository(popularMoviesApi, responseHandler)
    }

    @Test
    fun `test getPopularMovies() when valid page is requested, then weather is returned`() =
        runBlocking {
            assertEquals(moviesResponse, repository.getPopularMovies(validPage))
        }

    @Test
    fun `test getPopularMovies() when invalid page is requested, then error is returned`() =
        runBlocking {
            assertEquals(errorResponse, repository.getPopularMovies(invalidPage))
        }
}