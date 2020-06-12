package com.denisbeck.tmdbmovieapp.screens.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.extensions.addChips
import com.denisbeck.tmdbmovieapp.extensions.insertImageOriginal
import com.denisbeck.tmdbmovieapp.extensions.toRunTime
import com.denisbeck.tmdbmovieapp.models.Credit
import com.denisbeck.tmdbmovieapp.models.Credits
import com.denisbeck.tmdbmovieapp.models.Movie
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.networking.Status
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        private val TAG = DetailFragment::class.java.simpleName
    }

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateMovieId(args.movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            movie.observe(viewLifecycleOwner, Observer { checkMovieStatus(it) })
            credits.observe(viewLifecycleOwner, Observer { checkCreditsStatus(it) })
        }
    }

    private fun checkCreditsStatus(resource: Resource<Credits>) {
        Log.d(TAG, "checkCreditsStatus: ${resource.status}")
        Log.d(TAG, "checkCreditsStatus: ${resource.message}")
        when (resource.status) {
            Status.SUCCESS -> updateRecycler(resource.data)
            Status.ERROR -> {
            }
            Status.LOADING -> {
            }
        }
    }

    private fun updateRecycler(data: Credits?) {
        data?.let {
            detail_credits_recycler.adapter = CreditsAdapter(it.credits())
        }
    }

    private fun checkMovieStatus(resource: Resource<Movie>) {
        when (resource.status) {
            Status.SUCCESS -> updateView(resource.data)
            Status.ERROR -> {
            }
            Status.LOADING -> {
            }
        }
    }

    private fun updateView(data: Movie?) {
        Log.d(TAG, "updateView: $data")
        data?.let { movie ->
            detail_poster.insertImageOriginal(movie.poster_path)
            detail_date_release.text = movie.release_date.substring(0..3)
            detail_genres_chips.addChips(movie.genres, layoutInflater, isCheckable = false)
            detail_overview.text = movie.overview
            detail_runtime.text = movie.runtime.toRunTime(resources)
            detail_title.text = movie.title
        }
    }

}