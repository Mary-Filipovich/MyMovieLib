package com.androidacademy.mymovielib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

class ActorsListAdapter : RecyclerView.Adapter<ActorViewHolder>() {

    private var actors: List<Actor> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }

}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.robert_downey)
            .fallback(R.drawable.ic_unloaded_image)
            .centerCrop()
    }

    private val actorName: TextView = itemView.findViewById(R.id.vha_iv_actor_name)
    private val actorPhoto: ShapeableImageView = itemView.findViewById(R.id.vha_iv_actor)

    fun bind(actor: Actor) {
        actorName.text = actor.nameActor

        Glide
            .with(context)
            .load(actor.photoActor)
            .apply(imageOption)
            .into(actorPhoto)
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context