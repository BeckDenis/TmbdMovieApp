package com.denisbeck.tmdbmovieapp.screens.base

import android.content.Context
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getFont
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.extensions.dpToPx
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class CommonNavAdapter(private val list: List<String>, val clickListener: (Int) -> Unit) : CommonNavigatorAdapter() {

    override fun getCount() = list.size

    override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
        val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
        colorTransitionPagerTitleView.run {
            typeface = getFont(context, R.font.allerta)
            normalColor = ContextCompat.getColor(context, R.color.primaryTextTransparent)
            selectedColor = ContextCompat.getColor(context, R.color.primaryText)
            when (index) {
                0 -> setPadding(context.dpToPx(26), 0, context.dpToPx(15), context.dpToPx(15))
                2 -> setPadding(context.dpToPx(15), 0, context.dpToPx(26), context.dpToPx(15))
                else -> setPadding(context.dpToPx(15), 0, context.dpToPx(15), context.dpToPx(15))
            }
            text = list[index]
            setOnClickListener { clickListener(index) }
            textSize = 26F

        }
        return colorTransitionPagerTitleView
    }

    override fun getIndicator(context: Context): IPagerIndicator? {
        return LinePagerIndicator(context).apply {
            setColors(ContextCompat.getColor(context, R.color.colorPrimary))
            mode = LinePagerIndicator.MODE_EXACTLY
            lineWidth = 150f
            lineHeight = 10f
            startInterpolator = AccelerateInterpolator()
            endInterpolator = DecelerateInterpolator(2.0f)
        }
    }

}