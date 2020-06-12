package com.denisbeck.tmdbmovieapp.screens.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.extensions.insertImageW500
import com.denisbeck.tmdbmovieapp.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val movieData: List<Movie>, val clickListener: (Int) -> Unit) :
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
            item_movie_poster.insertImageW500(movie.poster_path)
            item_movie_name.text = movie.title
            item_movie_vote.text = movie.vote_average.toString()
            setOnClickListener { clickListener(movie.id) }
        }
    }
}