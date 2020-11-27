package com.androidacademy.mymovielib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), MoviesListFragment.OnCardClickListener,
    MovieDetailFragment.OnBackButtonPressedListener {

    private var movieDetailFragment: MovieDetailFragment? = null
    private var moviesListFragment: MoviesListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            moviesListFragment = MoviesListFragment.newInstance()
            moviesListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.am_container, this, MOVIES_LIST_FRAGMENT_TAG)
                    .commit()
            }
        }
    }

    override fun onCardClick() {
        movieDetailFragment = MovieDetailFragment.newInstance()
        movieDetailFragment?.apply {
            supportFragmentManager.beginTransaction()
                .replace(R.id.am_container, this, MOVIE_DETAILS_FRAGMENT_TAG)
                .commit()
        }

    }

    override fun onBackButtonPressed() {
        moviesListFragment?.apply {
            supportFragmentManager.beginTransaction()
                .replace(R.id.am_container, this, MOVIES_LIST_FRAGMENT_TAG)
                .commit()
        }
    }

    companion object {
        private const val LOG_TAG = "MainActivity"
        private const val MOVIE_DETAILS_FRAGMENT_TAG = "MovieDetailFragment"
        private const val MOVIES_LIST_FRAGMENT_TAG = "MoviesListFragment"
    }

}