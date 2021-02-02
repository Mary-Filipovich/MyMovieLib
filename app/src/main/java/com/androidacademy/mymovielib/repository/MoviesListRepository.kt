package com.androidacademy.mymovielib.repository

import android.content.Context
import com.androidacademy.mymovielib.data.loadMovies
import kotlinx.coroutines.withContext

class MoviesListRepository (private val context: Context) {

    suspend fun loadMoviesFromJson() = loadMovies(context)
}