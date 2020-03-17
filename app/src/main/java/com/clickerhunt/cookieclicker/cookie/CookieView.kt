package com.clickerhunt.cookieclicker.cookie

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.clickerhunt.cookieclicker.R

class CookieView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var listCookies = mutableListOf<CookieInfo>()

    private val cookieBitmap =
        ContextCompat.getDrawable(context, R.drawable.cookie_medium)!!.toBitmap()
    private val paintBitmap = Paint()
    private val paintText = Paint().apply {

    }

    fun showCookie(x: Float, y: Float) {
        Log.d("COOKIE_VIEW", "show cookie $x $y")
        listCookies.add(CookieInfo(PointF(x, y)))
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val iterator = listCookies.iterator()
        Log.d("COOKIE_VIEW", "listCookies size ${listCookies.size}")
        while (iterator.hasNext()) {
            val cookie = iterator.next()

            if (cookie.alpha in 5..255) {
                cookie.alpha -= 5
                Log.d("COOKIE_VIEW", "alpha is: ${cookie.alpha}")
                invalidate()
            } else {
                iterator.remove()
            }

            paintBitmap.alpha = cookie.alpha
            canvas.drawBitmap(cookieBitmap, cookie.point.x - cookieBitmap.width/2, cookie.point.y - cookieBitmap.height/2, paintBitmap)
        }
    }

    data class CookieInfo(val point: PointF, var alpha: Int = 255)
}