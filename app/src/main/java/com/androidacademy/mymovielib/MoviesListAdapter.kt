package com.androidacademy.mymovielib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.mymovielib.data.Movie
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MoviesListAdapter(var listener: OnItemClickListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            listener.onClick(movie)
        }
        val likeButton: ImageView = holder.itemView.findViewById(R.id.vhm_iv_like)
        likeButton.setOnClickListener {
//            listener.onLikeClick(position, movie.id, movie.like)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
    }

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val movieTitle: TextView = itemView.findViewById(R.id.vhm_tv_movie_title)
    private val movieDuration: TextView = itemView.findViewById(R.id.vhm_tv_duration)
    private val reviewsNumber: TextView = itemView.findViewById(R.id.vhm_tv_review_number)
    private val ageLimit: TextView = itemView.findViewById(R.id.vhm_tv_age_limit)
    private val tagLine: TextView = itemView.findViewById(R.id.vhm_tv_tag_line)
    private val movieRating: AppCompatRatingBar = itemView.findViewById(R.id.vhm_rating_bar_stars)
    private val likeButton: ImageView = itemView.findViewById(R.id.vhm_iv_like)
    private val poster: ShapeableImageView = itemView.findViewById(R.id.vhm_siv_card_poster)

    fun bind(movie: Movie) {
        movieTitle.text = movie.title
        movieDuration.text = context.getString(R.string.minutes, movie.runtime)
        reviewsNumber.text = context.getString(R.string.reviews, movie.numberOfRatings)
        ageLimit.text = context.getString(R.string.age_limit, movie.minimumAge)
        tagLine.text = movie.genres.joinToString(", ") { it.name }
        movieRating.rating = movie.ratings/2

//        likeButton.setColorFilter(
//            if (movie.like) ContextCompat.getColor(
//                itemView.context,
//                R.color.radical_red
//            ) else ContextCompat.getColor(itemView.context, R.color.white_75_opacity)
//        )

        Glide
            .with(context)
            .load(movie.poster)
            .placeholder(R.drawable.card_poster)
            .fallback(R.drawable.ic_unloaded_image)
            .into(poster)
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

interface OnItemClickListener {
    fun onClick(movie: Movie)
    fun onLikeClick(position: Int, movieId: Int, isLiked: Boolean)
}