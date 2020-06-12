package com.denisbeck.tmdbmovieapp.screens.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.screens.boxoffice.BoxOfficeFragment
import com.denisbeck.tmdbmovieapp.screens.coming.ComingFragment
import com.denisbeck.tmdbmovieapp.screens.popular.PopularFragment
import kotlinx.android.synthetic.main.fragment_base.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class BaseFragment : Fragment(R.layout.fragment_base) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mTitleDataList = listOf("Popular", "Box office", "Coming")
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.adapter = CommonNavAdapter(mTitleDataList) {
            view_pager.currentItem = it
        }

        magic_indicator.navigator = commonNavigator

        val ppp = listOf(
            PopularFragment.newInstance(),
            BoxOfficeFragment.newInstance(),
            ComingFragment.newInstance()
        )

        view_pager.adapter = ViewPagerAdapter(childFragmentManager, ppp)
        ViewPagerHelper.bind(magic_indicator, view_pager)
    }
}