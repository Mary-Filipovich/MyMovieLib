package com.androidacademy.mymovielib

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.mymovielib.data.Movie
import com.androidacademy.mymovielib.viewmodels.MoviesListViewModel
import com.androidacademy.mymovielib.viewmodels.MoviesListViewModelFactory


class MoviesListFragment : Fragment() {

    interface OnCardClickListener {
        fun onCardClick(movieId: Int)
    }

    private var movieRecyclerView: RecyclerView? = null
    private var moviesListFromJson: List<Movie> = ArrayList()
    private var listener: OnCardClickListener? = null
    private lateinit var adapter: MoviesListAdapter

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setMoviesListAdapter()
        subscribeOnLiveData()
    }


    private fun initViews(view: View) {
        movieRecyclerView = view.findViewById(R.id.fml_rv_movies)
    }

    private fun setMoviesListAdapter() {
        movieRecyclerView?.layoutManager = GridLayoutManager(
            activity?.applicationContext,
            resources.getInteger(R.integer.spanCount),
            GridLayoutManager.VERTICAL,
            false
        )
        adapter = MoviesListAdapter(cardClickListener)
        movieRecyclerView?.adapter = adapter
    }

    private fun subscribeOnLiveData() {
        viewModel.movies.observe(this.viewLifecycleOwner, {
            moviesListFromJson = it
            updateAdapter()
        })
    }

    private fun updateAdapter() {
        adapter.submitList(moviesListFromJson)
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

    override fun onResume() {
        super.onResume()
        loadListMovies()
    }

    private fun loadListMovies() {
        viewModel.loadMoviesList()
    }

    private val cardClickListener = object : OnItemClickListener {
        override fun onClick(movieId: Int) {
            listener?.onCardClick(movieId)
        }

        override fun onLikeClick(position: Int, movieId: Int, isLiked: Boolean) {
            viewModel.setLike(position, movieId, isLiked)
        }

    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }

}