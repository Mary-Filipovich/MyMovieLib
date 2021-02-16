package com.androidacademy.mymovielib.repository

import android.content.Context
import com.androidacademy.mymovielib.data.loadMovies

class MovieDetailRepository (private val context: Context) {

    suspend fun loadMovieFromJson(movieId: Int) = loadMovies(context).first { movie -> movie.id == movieId }
}