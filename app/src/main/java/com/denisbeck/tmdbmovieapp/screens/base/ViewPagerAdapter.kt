package com.denisbeck.tmdbmovieapp.screens.base

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager, private val list: List<Fragment>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAG = ViewPagerAdapter::class.java.simpleName
    }

    override fun getItem(position: Int): Fragment {
        Log.d(TAG, "getItem: $position")
        return list[position]
    }

    override fun getCount() = list.size

}