package com.denisbeck.tmdbmovieapp.extensions

import android.content.Context
import android.content.res.Resources
import com.denisbeck.tmdbmovieapp.R

fun Int.toRunTime(resources: Resources): String {
    var time = this
    var hour = 0
    while (time > 60) {
        time -= 60
        hour++
    }
    return resources.getString(R.string.runtime, hour, time)
}
