package com.androidacademy.mymovielib.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidacademy.mymovielib.repository.MoviesListRepository

class MoviesListViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> {
            val moviesListRepository = MoviesListRepository(context)
            MoviesListViewModel (moviesListRepository)
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}