package com.denisbeck.tmdbmovieapp.screens.popular

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.denisbeck.tmdbmovieapp.BuildConfig
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.models.PopularMovies
import com.denisbeck.tmdbmovieapp.networking.PopularMoviesApi
import com.denisbeck.tmdbmovieapp.networking.ServiceBuilder
import com.denisbeck.tmdbmovieapp.screens.OffsetItemDecoration
import kotlinx.android.synthetic.main.fragment_popular.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragment : Fragment(R.layout.fragment_popular) {

    companion object {
        private val TAG = PopularFragment::class.java.simpleName
    }

    private lateinit var movieAdapter: MovieAdapter
    private var page = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter(mutableListOf()) {
            page++
            getPopularMovies(page)
        }
        val snapHelper = PagerSnapHelper()
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(popular_recycler_view)
        popular_recycler_view.run {
            adapter = movieAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(OffsetItemDecoration(requireContext()))
        }
        getPopularMovies(page)
    }

    private fun getPopularMovies(page: Int) {
        val request = ServiceBuilder.buildService(PopularMoviesApi::class.java)
        val call = request.getPopularMovies(BuildConfig.API_KEY, page)

        call.enqueue(object : Callback<PopularMovies> {
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                Log.d(TAG, "onResponse: ")
                if (response.isSuccessful){
                    Log.d(TAG, "onResponse: success")
                    response.body()?.let {
                        movieAdapter.movieData += it.results
                        movieAdapter.notifyDataSetChanged()
                    }

                }
            }
            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}