package com.denisbeck.tmdbmovieapp.screens.popular

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.extensions.addChips
import com.denisbeck.tmdbmovieapp.extensions.showToast
import com.denisbeck.tmdbmovieapp.models.Genres
import com.denisbeck.tmdbmovieapp.models.Movies
import com.denisbeck.tmdbmovieapp.networking.Resource
import com.denisbeck.tmdbmovieapp.networking.Status
import com.denisbeck.tmdbmovieapp.utils.OffsetItemDecoration
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment(val clickListener: (Int) -> Unit) : Fragment(R.layout.fragment_popular) {

    companion object {
        private val TAG = PopularFragment::class.java.simpleName
    }

    private val viewModel: PopularViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tuneRecyclerView()
        viewModel.movies.observe(viewLifecycleOwner, Observer { checkMoviesStatus(it) })
        viewModel.genres.observe(viewLifecycleOwner, Observer { checkGenresStatus(it) })
        popular_genres_chips.setOnCheckedChangeListener { _, checkedId ->
            viewModel.updateGenres(checkedId)
            Log.d(TAG, "onViewCreated: $checkedId")
        }
    }

    private fun checkGenresStatus(it: Resource<Genres>) {
        when (it.status) {
            Status.SUCCESS -> popular_genres_chips.addChips(it.data?.genres, layoutInflater)
            Status.ERROR -> Log.e(TAG, "checkGenresStatus: ${it.message}")
            Status.LOADING -> {
            }
        }
    }

    private fun checkMoviesStatus(it: Resource<Movies>) {
        when (it.status) {
            Status.SUCCESS -> updateRecyclerAndShowProgressBar(it.data)
            Status.ERROR -> showErrorToastAndHideProgressBar(it.message)
            Status.LOADING -> showProgressBar()
        }
    }

    private fun showErrorToastAndHideProgressBar(message: String?) {
        Log.e(TAG, "showErrorToastAndHideProgressBar: $message")
        popular_progress_bar.visibility = View.GONE
        showToast(message)
    }

    private fun showProgressBar() {
        if (popular_recycler_view.adapter == null) popular_progress_bar.visibility = View.VISIBLE
    }

    private fun updateRecyclerAndShowProgressBar(data: Movies?) {
        Log.d(TAG, "updateRecyclerAndShowProgressBar: called")
        updateRecycler(data)
        popular_progress_bar.visibility = View.GONE
    }

    private fun updateRecycler(movies: Movies?) {
        movies?.let {
            popular_recycler_view.adapter = MovieAdapter(movies.results) { clickListener(it) }
        }
    }

    private fun tuneRecyclerView() {
        val snapHelper = PagerSnapHelper()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(popular_recycler_view)
        popular_recycler_view.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(OffsetItemDecoration(requireContext()))
        }
    }

}