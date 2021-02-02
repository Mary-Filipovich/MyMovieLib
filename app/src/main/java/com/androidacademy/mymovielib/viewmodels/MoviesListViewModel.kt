package com.androidacademy.mymovielib.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidacademy.mymovielib.data.Movie
import com.androidacademy.mymovielib.repository.MoviesListRepository
import kotlinx.coroutines.launch

class MoviesListViewModel (private val repository: MoviesListRepository): ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>>
    get() = _movies

    fun loadMoviesList() {
        viewModelScope.launch {
            _movies.value = repository.loadMoviesFromJson()
        }
    }

    fun setLike(position: Int, movieId: Int, isLiked: Boolean) {
        val newList: MutableList<Movie> = _movies.value.orEmpty().toMutableList()
        newList[position] =
            _movies.value.orEmpty().first { movie -> movie.id == movieId }.copy(like = isLiked)
        _movies.value = newList
    }
}