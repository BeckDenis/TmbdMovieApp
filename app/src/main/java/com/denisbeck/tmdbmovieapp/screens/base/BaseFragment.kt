package com.denisbeck.tmdbmovieapp.screens.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.screens.boxoffice.BoxOfficeFragment
import com.denisbeck.tmdbmovieapp.screens.coming.ComingFragment
import com.denisbeck.tmdbmovieapp.screens.popular.PopularFragment
import kotlinx.android.synthetic.main.fragment_base.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BaseFragment : Fragment(R.layout.fragment_base) {

    companion object {
        private val TAG = BaseFragment::class.java.simpleName
    }
    private val sharedViewModel: SharedViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mTitleDataList = listOf("Popular", "Box office", "Coming")
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.adapter = CommonNavAdapter(mTitleDataList) { titleId ->
            view_pager.currentItem = titleId
        }

        magic_indicator.navigator = commonNavigator

        val fragments = listOf(
            PopularFragment(),
            BoxOfficeFragment(),
            ComingFragment()
        )

        view_pager.adapter = ViewPagerAdapter(childFragmentManager, fragments)
        ViewPagerHelper.bind(magic_indicator, view_pager)
        sharedViewModel.goToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                sharedViewModel.updateMovieId(null)
                val action = BaseFragmentDirections.actionBaseFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        })
    }
}