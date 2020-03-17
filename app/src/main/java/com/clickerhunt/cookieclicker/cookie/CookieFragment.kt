package com.clickerhunt.cookieclicker.cookie

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.clickerhunt.cookieclicker.R
import kotlinx.android.synthetic.main.fragment_cookie.*
import kotlin.math.pow
import kotlin.math.sqrt

class CookieFragment(private val listenerCookie: Listener) : Fragment(R.layout.fragment_cookie) {

    private var radius = 0
    private var offset = Point()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cookie_click.setOnTouchListener(touchListener)
    }


    private val touchListener = View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {

            radius = cookie_image.width / 2
            offset.x = (cookie_image.x + cookie_image.width / 2).toInt()
            offset.y = (cookie_image.y + cookie_image.height / 2).toInt()

            val sqrt = sqrt((event.x - offset.x).pow(2) + (event.y - offset.y).pow(2))
            if (sqrt < radius) {
                cookie_click.showCookie(event.x, event.y, "+1")
                listenerCookie.onCookieClicked()
            }
        }
        return@OnTouchListener true
    }


    interface Listener {
        fun onCookieClicked()
    }
}