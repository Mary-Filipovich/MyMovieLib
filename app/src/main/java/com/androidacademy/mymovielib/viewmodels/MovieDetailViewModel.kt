package com.androidacademy.mymovielib.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidacademy.mymovielib.data.Actor
import com.androidacademy.mymovielib.data.Movie
import com.androidacademy.mymovielib.repository.MovieDetailRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value = repository.loadMovieFromJson(movieId)
        }
    }

}