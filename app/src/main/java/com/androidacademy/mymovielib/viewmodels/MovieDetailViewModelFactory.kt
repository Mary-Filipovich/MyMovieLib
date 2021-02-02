package com.androidacademy.mymovielib.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidacademy.mymovielib.repository.MovieDetailRepository

class MovieDetailViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailViewModel::class.java -> {
            val movieDetailRepository = MovieDetailRepository(context)
            MovieDetailViewModel (movieDetailRepository)
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}