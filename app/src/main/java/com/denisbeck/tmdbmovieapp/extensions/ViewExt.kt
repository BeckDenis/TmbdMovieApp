package com.denisbeck.tmdbmovieapp.extensions

import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.glide.RequestDrawableListenerAdapter
import com.denisbeck.tmdbmovieapp.models.Genre
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ImageView.insertImageW185(posterId: String?) {
    Glide.with(context).load("https://image.tmdb.org/t/p/w185$posterId").into(this)
}

fun ImageView.insertImageW500(posterId: String?) {
    Glide.with(context).load("https://image.tmdb.org/t/p/w500$posterId").into(this)
}

fun ImageView.insertImageOriginal(posterId: String?, listener: (() -> Unit)? = null) {
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/original$posterId")
        .listener(RequestDrawableListenerAdapter(listener))
        .into(this)
}

fun ChipGroup.addChips(
    data: List<Genre>?, layoutInflater: LayoutInflater, isCheckable: Boolean = true
) {
    data?.let {
        it.forEach {
            val view = layoutInflater.inflate(R.layout.item_genre, null, false)
            val chip = (view as Chip).apply {
                text = it.name
                id = it.id
                setCheckable(isCheckable)
            }
            this.addView(chip)
        }
    }
}