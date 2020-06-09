package com.denisbeck.tmdbmovieapp.screens.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denisbeck.tmdbmovieapp.R
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val movieData: List<String>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movieData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieData[position]
        holder.view.item_movie_name.text = movie
    }
}