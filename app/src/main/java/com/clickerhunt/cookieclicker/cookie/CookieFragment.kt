package com.clickerhunt.cookieclicker.cookie

import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent.*
import android.view.View
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.dpToPx
import com.clickerhunt.cookieclicker.settings.SettingsManager
import kotlinx.android.synthetic.main.fragment_cookie.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class CookieFragment : Fragment(R.layout.fragment_cookie) {

    private var radius = 0
    private val offset = Point()
    private val mainBoostCookiePoint = PointF()

    private val cookiesDao = AppDatabase.instance.configurationDao()
    private val configurationLive = cookiesDao.getConfiguration()
    private val configuration get() = configurationLive.value!!

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
        configurationLive.observe(viewLifecycleOwner) {}
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
        val eventX = event.getX(event.actionIndex)
        val eventY = event.getY(event.actionIndex)
        val actionMasked = event.actionMasked
        Log.d(
            this::class.java.simpleName,
            "event: masked=$actionMasked action=${event.action} index=${event.actionIndex} pointerCount=${event.pointerCount} at [$eventX, $eventY]"
        )

        if (actionMasked == ACTION_DOWN || actionMasked == ACTION_POINTER_DOWN) {

            radius = cookie_image.width / 2
            offset.x = (cookie_image.left + cookie_image.width / 2)
            offset.y = (cookie_image.top + cookie_image.height / 2)

            val sqrt = sqrt((eventX - offset.x).pow(2) + (eventY - offset.y).pow(2))
            if (sqrt < radius) {
                if (event.action == ACTION_DOWN) {
                    cookie_image.run {
                        val centerX = right - radius
                        val centerY = bottom - radius
                        val partX = (centerX - eventX) / radius
                        val partY = (centerY - eventY) / radius
                        val angle = 5f
                        rotationX = partY * angle
                        rotationY = -partX * angle
                    }
                }

                val scale = 0.99f
                if (event.action == ACTION_DOWN) {
                    cookie_image.scaleX = scale
                    cookie_image.scaleY = scale
                }

                SettingsManager.vibrateShort()
                cookie_click.showCookie(eventX, eventY, "+1")
                cookiesDao.addCookiesCount(1)
            }
        }

        if (event.action == ACTION_UP || event.action == ACTION_CANCEL) {
            cookie_image.scaleX = 1f
            cookie_image.scaleY = 1f
            cookie_image.rotationX = 0f
            cookie_image.rotationY = 0f
        }

        return@OnTouchListener true
    }


}