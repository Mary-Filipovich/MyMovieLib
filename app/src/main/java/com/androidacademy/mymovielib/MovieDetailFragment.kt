package com.androidacademy.mymovielib

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class MovieDetailFragment : Fragment() {
    private var mListener: OnBackButtonPressedListener? = null

    interface OnBackButtonPressedListener {
        fun onBackButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val backButton: TextView = view.findViewById(R.id.fmd_tv_back_button)
        backButton.setOnClickListener { mListener?.onBackButtonPressed() }

        return view
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
        fun newInstance() = MovieDetailFragment()
    }
}