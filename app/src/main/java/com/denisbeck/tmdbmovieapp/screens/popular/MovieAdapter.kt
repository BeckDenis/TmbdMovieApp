package com.denisbeck.tmdbmovieapp.screens.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val movieData: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movieData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieData[position]
        holder.view.run {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500${movie.poster_path}").into(item_movie_poster)
            item_movie_name.text = movie.title
            item_movie_vote.text = movie.vote_average.toString()
        }
    }
}