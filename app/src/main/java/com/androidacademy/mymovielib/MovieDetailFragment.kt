package com.androidacademy.mymovielib

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.mymovielib.data.Actor
import com.androidacademy.mymovielib.data.Movie
import com.androidacademy.mymovielib.viewmodels.MovieDetailViewModel
import com.androidacademy.mymovielib.viewmodels.MovieDetailViewModelFactory
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment() {
    private var mListener: OnBackButtonPressedListener? = null

    private var backButton: TextView? = null
    private var poster: ImageView? = null
    private var ageLimit: TextView? = null
    private var movieTitle: TextView? = null
    private var tagLine: TextView? = null
    private var rating: AppCompatRatingBar? = null
    private var reviews: TextView? = null
    private var storyline: TextView? = null
    private var actorsRecyclerView: RecyclerView? = null
    private val actorsAdapter: ActorsListAdapter = ActorsListAdapter()
    private var actors = listOf<Actor>()

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setActorsListAdapter()
        subscribeOnLiveData()

        backButton?.setOnClickListener { mListener?.onBackButtonPressed() }

        val movieId = requireNotNull(arguments?.getInt(KEY_MOVIE_ID))
        viewModel.loadMovie(movieId)
    }

    private fun initViews(view: View) {
        backButton = view.findViewById(R.id.fmd_tv_back_button)
        poster = view.findViewById(R.id.fmd_iv_big_poster)
        ageLimit = view.findViewById(R.id.fmd_tv_age_limit)
        movieTitle = view.findViewById(R.id.fmd_tv_movie_title)
        tagLine = view.findViewById(R.id.fmd_tv_tag_line)
        rating = view.findViewById(R.id.fmd_rating_bar_stars)
        reviews = view.findViewById(R.id.fmd_tv_review_number)
        storyline = view.findViewById(R.id.fmd_tv_storyline)
        actorsRecyclerView = view.findViewById(R.id.fmd_rv_actors)
    }

    private fun setActorsListAdapter() {
        actorsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(
                activity?.applicationContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = actorsAdapter
        }
    }

    private fun subscribeOnLiveData() {
        viewModel.movie.observe(this.viewLifecycleOwner, {
            updateUI(it)
        })
    }

    private fun updateUI(movie: Movie) {
        ageLimit?.text = getString(R.string.age_limit, movie.minimumAge)
        movieTitle?.text = movie.title
        tagLine?.text = movie.genres.joinToString(", ") { it.name }
        rating?.rating = movie.ratings / 2
        reviews?.text = getString(R.string.reviews, movie.numberOfRatings)
        storyline?.text = movie.overview
        poster?.let {
            Glide
                .with(this)
                .load(movie.backdrop)
                .placeholder(R.drawable.image_poster)
                .fallback(R.drawable.ic_unloaded_image)
                .into(it)
        }
        actorsAdapter.submitList(movie.actors)
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
        private const val KEY_MOVIE_ID = "movie"
        fun newInstance(movieId: Int) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_MOVIE_ID, movieId)
            }
        }
    }

    interface OnBackButtonPressedListener {
        fun onBackButtonPressed()
    }
}