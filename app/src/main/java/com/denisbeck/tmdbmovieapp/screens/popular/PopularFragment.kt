package com.denisbeck.tmdbmovieapp.screens.popular

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.screens.OffsetItemDecoration
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment(R.layout.fragment_popular) {

    companion object {
        private val TAG = PopularFragment::class.java.simpleName
    }

    private val viewModel: ExampleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) viewModel.getPopularMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val snapHelper = PagerSnapHelper()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(popular_recycler_view)

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            popular_recycler_view.run {
                adapter = MovieAdapter(movies.results)
                layoutManager = linearLayoutManager
                addItemDecoration(OffsetItemDecoration(requireContext()))
            }
        })
    }


}