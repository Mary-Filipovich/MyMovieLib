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
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment() {
    private var mListener: OnBackButtonPressedListener? = null

    private var actorsRecyclerView: RecyclerView? = null
    private var actors = listOf<Actor>()
    private lateinit var movie: Movie

    interface OnBackButtonPressedListener {
        fun onBackButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movie = TestMoviesData().getMovieById(requireArguments().getInt(KEY_MOVIE_ID))
        actors = movie.listOfActors
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
        ageLimit.text = movie.rated
        movieTitle.text = movie.nameMovie
        tagLine.text = movie.movieGenre
        rating.rating = movie.rating
        reviews.text = "${movie.reviews} REVIEWS"
        storyline.text = movie.description

        Glide
            .with(this)
            .load(movie.detailPoster)
            .placeholder(R.drawable.image_poster)
            .fallback(R.drawable.ic_unloaded_image)
            .into(poster)

        actorsRecyclerView = view.findViewById(R.id.fmd_rv_actors)
        actorsRecyclerView?.adapter = ActorsListAdapter()
        actorsRecyclerView?.layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        (actorsRecyclerView?.adapter as? ActorsListAdapter)?.apply {
            bindActors(actors)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackButtonPressedListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        mListener = null
        actorsRecyclerView = null
        super.onDetach()
    }

    companion object {
        private const val KEY_MOVIE_ID = "movieId"
        private const val TAG = "MovieDetailFragment"
        fun newInstance(idMovie: Int) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_MOVIE_ID, idMovie)
            }
        }
    }
}