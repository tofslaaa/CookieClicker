package com.clickerhunt.cookieclicker.cookie

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.dpToPx
import kotlinx.android.synthetic.main.fragment_cookie.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class CookieFragment(private val listenerCookie: Listener) : Fragment(R.layout.fragment_cookie) {

    private var radius = 0
    private var offset = Point()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CookieManager.addedCookies.observe(viewLifecycleOwner) {
            val diameter = cookie_image.bottom - cookie_image.top
            val x = (cookie_image.left..cookie_image.right).random()
            val hord = 2 * sqrt(x * (diameter - x).toFloat())
            val y1 = (diameter - hord) / 2 + cookie_image.top
            val y = (y1.toInt()..((y1 + hord).toInt())).random()

            for (i in 0 until it) {
                val offset = 32.dpToPx()
                val xChild = Random.nextInt(x - offset, x + offset)
                val yChild = Random.nextInt(y - offset, y + offset)
                cookie_click.showCookie(xChild.toFloat(), yChild.toFloat(), "")
            }
            cookie_click.showCookie(x.toFloat(), y.toFloat(), "+$it")
        }


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