package com.androidacademy.mymovielib

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class MoviesListFragment : Fragment() {

    interface OnCardClickListener {
        fun onCardClick(idMovie: Int)
    }

    private var movieRecyclerView: RecyclerView? = null
    private var testMoviesData = TestMoviesData().getMoviesList()
    private var listener: OnCardClickListener? = null
    private lateinit var adapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieRecyclerView = view.findViewById(R.id.fml_rv_movies)
        adapter = MoviesListAdapter(cardClickListener)
        movieRecyclerView?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        updateMovies()
    }

    private fun updateMovies() {
        adapter.bindMovies(testMoviesData)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCardClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        movieRecyclerView = null
        super.onDetach()
    }

    private val cardClickListener = object : OnItemClickListener {
        override fun onClick(movie: Movie) {
            listener?.onCardClick(movie.id)
        }

        override fun onLikeClick(position: Int, movieId: Int, isLiked: Boolean) {
            val newList: MutableList<Movie> = testMoviesData.toMutableList()
            newList[position] = testMoviesData.first { movie -> movie.id == movieId }.copy(like = !isLiked)
            adapter.bindMovies(newList)
            val diffCallback = MoviesDiffUtilsCallback(testMoviesData, newList)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(adapter)
            testMoviesData = newList
        }

    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }

}