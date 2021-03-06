package com.denisbeck.tmdbmovieapp.screens.detail

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.anim.starAnimation
import com.denisbeck.tmdbmovieapp.extensions.*
import com.denisbeck.tmdbmovieapp.models.Credits
import com.denisbeck.tmdbmovieapp.models.Movie
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.networking.Status
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.rating_card
import kotlinx.android.synthetic.main.rating_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        private val TAG = DetailFragment::class.java.simpleName
    }

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private var displayWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateMovieId(args.movieId)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayWidth = displayMetrics.widthPixels
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRatingCardWidth()
        detail_back.setOnClickListener { requireActivity().onBackPressed() }
        rating_card_like_image.setOnClickListener { startAnimation() }
        viewModel.run {
            movie.observe(viewLifecycleOwner, Observer { checkMovieStatus(it) })
            credits.observe(viewLifecycleOwner, Observer { checkCreditsStatus(it) })
        }
        coordinateMotion()
    }

    private fun setRatingCardWidth() {
        //we need 26dp indent but we have round corner which we don't show = (44dp (round corners) - 26dp = 18dp)
        val width = displayWidth + (requireContext().dpToPx(18))
        motion_layout.getConstraintSet(R.id.start)?.constrainWidth(R.id.rating_card, width)
        motion_layout.getConstraintSet(R.id.end)?.constrainWidth(R.id.rating_card, width)
    }

    private fun coordinateMotion() {
        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appbar_layout.totalScrollRange.toFloat()
            motion_layout.progress = seekPosition
        }

        appbar_layout.addOnOffsetChangedListener(listener)
    }

    private fun checkMovieStatus(resource: Resource<Movie>) {
        when (resource.status) {
            Status.SUCCESS -> updateView(resource.data)
            Status.ERROR -> {
                showErrorToastAndHideProgressBar(resource.message)
            }
            Status.LOADING -> {
                showProgressBar()
            }
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

    private fun updateView(data: Movie?) {
        Log.d(TAG, "updateView: $data")
        data?.let { movie ->
            detail_poster.insertImageOriginal(movie.poster_path) {
                detail_progress_bar.visibility = View.GONE
            }
            detail_date_release.text = movie.release_date.substring(0..3)
            detail_genres_chips.addChips(movie.genres, layoutInflater, isCheckable = false)
            detail_overview.text = movie.overview
            detail_runtime.text = movie.runtime.toRunTime(resources)
            detail_title.text = movie.title
            rating_card_vote.text = getString(R.string.vote, movie.vote_average)
            rating_card_vote_number.text = movie.vote_count.toString()
        }
    }

    private fun startAnimation() {
        rating_card_like_image.starAnimation()
    }

    private fun showErrorToastAndHideProgressBar(message: String?) {
        Log.e(TAG, "showErrorToastAndHideProgressBar: $message")
        detail_progress_bar.visibility = View.GONE
        showToast(message)
    }

    private fun showProgressBar() {
        detail_progress_bar.visibility = View.VISIBLE
    }

}