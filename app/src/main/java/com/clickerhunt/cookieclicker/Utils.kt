package com.clickerhunt.cookieclicker

import android.content.res.Resources
import android.util.DisplayMetrics

fun Int.dpToPx() =
    this * (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun Int.spToPx() = this * Resources.getSystem().displayMetrics.scaledDensity