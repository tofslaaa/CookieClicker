package com.clickerhunt.cookieclicker.cookie

import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.dpToPx
import com.clickerhunt.cookieclicker.settings.SettingsManager
import kotlinx.android.synthetic.main.fragment_cookie.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class CookieFragment(private val listenerCookie: Listener) : Fragment(R.layout.fragment_cookie) {

    private var radius = 0
    private val offset = Point()
    private val mainBoostCookiePoint = PointF()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CookieManager.addedCookies.value = 0
        CookieManager.addedCookies.observe(viewLifecycleOwner) {
            if (cookie_image.width == 0 || it == 0) return@observe
            val (x, y) = randomCookiePoint()

            for (i in 0 until (it - 1).coerceAtMost(10)) {
                val offset = 32.dpToPx().toDouble()
                val xChild = Random.nextDouble(x - offset, x + offset).toFloat()
                val yChild = Random.nextDouble(y - offset, y + offset).toFloat()
                cookie_click.showCookie(xChild, yChild, "")
            }
            cookie_click.showCookie(x, y, "+$it")
        }


        cookie_click.setOnTouchListener(touchListener)
    }

    private fun randomCookiePoint(): PointF {
        val diameter = cookie_image.bottom - cookie_image.top
        val x = (cookie_image.left..cookie_image.right).random()
        val offsetX = x - cookie_image.left
        val hord = 2 * sqrt(offsetX * (diameter - offsetX).toFloat())
        val y1 = (diameter - hord) / 2 + cookie_image.top
        val y = (y1.toInt()..((y1 + hord).toInt())).random()
        mainBoostCookiePoint.x = x.toFloat()
        mainBoostCookiePoint.y = y.toFloat()
        return mainBoostCookiePoint
    }

    private val touchListener = View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {

            radius = cookie_image.width / 2
            offset.x = (cookie_image.left + cookie_image.width / 2)
            offset.y = (cookie_image.top + cookie_image.height / 2)

            val sqrt = sqrt((event.x - offset.x).pow(2) + (event.y - offset.y).pow(2))
            if (sqrt < radius) {
                SettingsManager.vibrateShort()
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