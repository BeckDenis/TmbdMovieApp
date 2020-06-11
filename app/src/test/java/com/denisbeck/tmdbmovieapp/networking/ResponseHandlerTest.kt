package com.denisbeck.tmdbmovieapp.networking

import com.denisbeck.tmdbmovieapp.models.Movies
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class ResponseHandlerTest {
    lateinit var responseHandler: ResponseHandler

    @Before
    fun setUp() {
        responseHandler = ResponseHandler()
    }

    @Test
    fun `when exception code is 401 then return unauthorised`() {
        val httpException = HttpException(Response.error<Movies>(401, mock()))
        val result = responseHandler.handleException<Movies>(httpException)
        assertEquals("Authentication failed: You do not have permissions to access the service.", result.message)
    }

    @Test
    fun `when timeout then return timeout error`() {
        val socketTimeoutException = SocketTimeoutException()
        val result = responseHandler.handleException<Movies>(socketTimeoutException)
        assertEquals("Timeout", result.message)
    }
}