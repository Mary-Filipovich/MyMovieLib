package com.androidacademy.mymovielib

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment


class MoviesListFragment : Fragment() {
    private var listener: OnCardClickListener? = null

    interface OnCardClickListener {
        fun onCardClick()
    }

    private var isMovieLiked = false
    private lateinit var likeButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_movies_list, container, false)

        val card: View = view.findViewById(R.id.fml_v_card)
        card.setOnClickListener {
            listener?.onCardClick()
        }

        likeButton = view.findViewById(R.id.fml_iv_like)
        likeButton.setOnClickListener {
            isMovieLiked = !isMovieLiked
            changeLikeState()
        }

        return view
    }

    private fun changeLikeState() {
        likeButton.setImageResource(
            if (isMovieLiked) R.drawable.ic_like_active else R.drawable.ic_like
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_IS_MOVIE_LIKED, isMovieLiked)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            isMovieLiked = savedInstanceState.getBoolean(KEY_IS_MOVIE_LIKED)
            changeLikeState()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCardClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    companion object {
        private const val KEY_IS_MOVIE_LIKED = "isMovieLiked"
        fun newInstance() = MoviesListFragment()
    }

}