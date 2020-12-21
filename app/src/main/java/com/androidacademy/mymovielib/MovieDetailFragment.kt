package com.androidacademy.mymovielib

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.mymovielib.data.Actor
import com.androidacademy.mymovielib.data.Movie
import com.androidacademy.mymovielib.data.loadMovies
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class MovieDetailFragment : Fragment() {
    private var mListener: OnBackButtonPressedListener? = null

    private val actorsAdapter: ActorsListAdapter = ActorsListAdapter()
    private var actors = listOf<Actor>()
    private var movie: Movie? = null
    private var coroutineScope = createScope()

    private fun createScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val movieId = requireArguments().getInt(KEY_MOVIE_ID)

        movie = requireArguments().getParcelable(KEY_MOVIE)
        actors = movie?.actors!!

//        Log.d(TAG, "movie: $movie")

        val backButton: TextView = view.findViewById(R.id.fmd_tv_back_button)
        val poster: ImageView = view.findViewById(R.id.fmd_iv_big_poster)
        val ageLimit: TextView = view.findViewById(R.id.fmd_tv_age_limit)
        val movieTitle: TextView = view.findViewById(R.id.fmd_tv_movie_title)
        val tagLine: TextView = view.findViewById(R.id.fmd_tv_tag_line)
        val rating: AppCompatRatingBar = view.findViewById(R.id.fmd_rating_bar_stars)
        val reviews: TextView = view.findViewById(R.id.fmd_tv_review_number)
        val storyline: TextView = view.findViewById(R.id.fmd_tv_storyline)

        backButton.setOnClickListener { mListener?.onBackButtonPressed() }
        if (movie != null) {
            ageLimit.text = getString(R.string.age_limit, movie!!.minimumAge)
            movieTitle.text = movie!!.title
            tagLine.text = movie!!.genres.joinToString(", ") { it.name }
            rating.rating = movie!!.ratings / 2
            reviews.text = getString(R.string.reviews, movie!!.numberOfRatings)
            storyline.text = movie!!.overview

            Glide
                .with(this)
                .load(movie!!.backdrop)
                .placeholder(R.drawable.image_poster)
                .fallback(R.drawable.ic_unloaded_image)
                .into(poster)
        }

        view.findViewById<RecyclerView>(R.id.fmd_rv_actors).apply {
            adapter = actorsAdapter
            layoutManager = LinearLayoutManager(
                activity?.applicationContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        actorsAdapter.bindActors(actors)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackButtonPressedListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    companion object {
        private const val KEY_MOVIE_ID = "movieId"
        private const val KEY_MOVIE = "movie"
        private const val TAG = "MovieDetailFragment"

        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
//                putInt(KEY_MOVIE_ID, idMovie)
                putParcelable(KEY_MOVIE, movie)
            }
        }
    }

    interface OnBackButtonPressedListener {
        fun onBackButtonPressed()
    }
}