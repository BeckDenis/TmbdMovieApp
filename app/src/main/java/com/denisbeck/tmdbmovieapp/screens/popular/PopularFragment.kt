package com.denisbeck.tmdbmovieapp.screens.popular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.screens.OffsetItemDecoration
import kotlinx.android.synthetic.main.fragment_popular.*

class PopularFragment : Fragment(R.layout.fragment_popular) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieList = listOf("Avengers", "Hitman", "Joker", "The King")
        val snapHelper = PagerSnapHelper()
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(popular_recycler_view)
        popular_recycler_view.run {
            adapter = MovieAdapter(movieList)
            layoutManager = linearLayoutManager
            addItemDecoration(OffsetItemDecoration(requireContext()))
        }
    }
}