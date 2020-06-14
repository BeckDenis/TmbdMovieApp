package com.denisbeck.tmdbmovieapp.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.denisbeck.tmdbmovieapp.R

fun ImageView.rotateAnimation(firstDrawableRef: Int, secondDrawableRef: Int) {
    val TAG = "pp"
    val animatorRotate =
        ObjectAnimator.ofFloat(this, View.ROTATION, -360f, 0f).apply {
            duration = 600
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    this@rotateAnimation.isClickable = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    this@rotateAnimation.isClickable = true
                }
            })
        }
    val animatorScaleXEnd =
        ObjectAnimator.ofFloat(this, View.SCALE_X, 1.2f, 1f).apply {
            duration = 300
        }
    val animatorScaleYEnd =
        ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.2f, 1f).apply {
            duration = 300
        }
    val animatorScaleXBegin =
        ObjectAnimator.ofFloat(this, View.SCALE_X, 1f, 1.2f).apply {
            duration = 300
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    val drawable = getDrawable()
                    this@rotateAnimation.setImageDrawable(drawable)
                    animatorScaleXEnd.start()
                    animatorScaleYEnd.start()
                }

                private fun getDrawable(): Drawable? {
                    Log.d(TAG, "getDrawable: ${this@rotateAnimation.drawable.constantState}")
                    Log.d(TAG, "getDrawable: ${ContextCompat.getDrawable(context, secondDrawableRef)?.constantState}")
                    Log.d(TAG, "getDrawable: ${ContextCompat.getDrawable(context, firstDrawableRef)?.constantState}")
                    return if (this@rotateAnimation.drawable.constantState == ContextCompat.getDrawable(
                            context,
                            firstDrawableRef
                        )?.constantState
                    ) {
                        ContextCompat.getDrawable(context, secondDrawableRef)
                    } else {
                        ContextCompat.getDrawable(context, firstDrawableRef)
                    }
                }
            })
        }
    val animatorScaleYBegin =
        ObjectAnimator.ofFloat(this, View.SCALE_Y, 1f, 1.2f).apply {
            duration = 300
        }
    animatorRotate.start()
    animatorScaleXBegin.start()
    animatorScaleYBegin.start()

}