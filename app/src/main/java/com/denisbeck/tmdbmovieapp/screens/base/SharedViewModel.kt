package com.denisbeck.tmdbmovieapp.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _goToDetail = MutableLiveData<Int>(null)
    val goToDetail: LiveData<Int>
        get() = _goToDetail

    fun updateMovieId(value: Int?) {
        _goToDetail.value = value
    }
}