package com.androidacademy.mymovielib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidacademy.mymovielib.data.Movie

class MainActivity : AppCompatActivity(), MoviesListFragment.OnCardClickListener,
    MovieDetailFragment.OnBackButtonPressedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.am_container,
                    MoviesListFragment.newInstance(),
                    MOVIES_LIST_FRAGMENT_TAG
                )
                .commit()
        }
    }

    override fun onCardClick(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.am_container,
                MovieDetailFragment.newInstance(movie),
                MOVIE_DETAILS_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onBackButtonPressed() {
        supportFragmentManager.popBackStack()
    }

    companion object {
        private const val LOG_TAG = "MainActivity"
        private const val MOVIE_DETAILS_FRAGMENT_TAG = "MovieDetailFragment"
        private const val MOVIES_LIST_FRAGMENT_TAG = "MoviesListFragment"
    }

}