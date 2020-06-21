package com.denisbeck.tmdbmovieapp.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.extensions.insertDrawable
import kotlinx.android.synthetic.main.fragment_detail.*

fun ImageView.starAnimation() {
    rotate()
    scaleTo(1.2f) {
        isClickable = false
        changeDrawable(R.drawable.star_border, R.drawable.star)
        scaleTo(1f) {
            isClickable = true
        }
    }
}

private fun ImageView.rotate(from: Float = -360f, to: Float = 0f, duration: Long = 600) {
    ObjectAnimator.ofFloat(this, View.ROTATION, from, to).apply {
        this.duration = duration
        start()
    }
}

private fun ImageView.scaleTo(scale: Float, duration: Long = 300, listenerEnd: (() -> Unit)? = null) {
    getScaleObjectAnimator(scale, scale).apply {
        animatorEndListener { listenerEnd?.let { it() } }
        this.duration = duration
        start()
    }
}

fun View.translateX(distance: Float) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, distance).apply {
        duration = 1000
    }.start()
}

private fun ImageView.getScaleObjectAnimator(x: Float, y: Float): ObjectAnimator {
    val scaleX1 = PropertyValuesHolder.ofFloat(View.SCALE_X, x)
    val scaleY1 = PropertyValuesHolder.ofFloat(View.SCALE_Y, y)
    return ObjectAnimator.ofPropertyValuesHolder(this, scaleX1, scaleY1)
}

private fun ObjectAnimator.animatorEndListener(listenerEnd: (() -> Unit)? = null) {
    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            listenerEnd?.let { it() }
        }
    })
}

private fun ImageView.changeDrawable(borderDrawable: Int, fillDrawable: Int) {
    if (tag == context.getString(R.string.image_tag_border)) {
        tag = context.getString(R.string.image_tag_fill)
        insertDrawable(fillDrawable)
    } else {
        tag = context.getString(R.string.image_tag_border)
        insertDrawable(borderDrawable)
    }
}