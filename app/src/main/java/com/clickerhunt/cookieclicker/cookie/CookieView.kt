package com.clickerhunt.cookieclicker.cookie

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.clickerhunt.cookieclicker.R
import com.clickerhunt.cookieclicker.dpToPx
import com.clickerhunt.cookieclicker.spToPx

class CookieView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var listCookies = mutableListOf<CookieInfo>()

    private val cookieBitmap =
        ContextCompat.getDrawable(context, R.drawable.cookie_medium)!!.toBitmap()
    private val paintBitmap = Paint()
    private val paintText = TextPaint().apply {
        textAlign = Paint.Align.CENTER
        color = ContextCompat.getColor(context, R.color.black)
        typeface = ResourcesCompat.getFont(context, R.font.noto_sans)
        textSize = 12.spToPx()
    }

    fun showCookie(x: Float, y: Float) {
        listCookies.add(CookieInfo(PointF(x, y)))
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val iterator = listCookies.iterator()
        while (iterator.hasNext()) {
            val cookie = iterator.next()

            if (cookie.alpha in 5..255) {
                cookie.alpha -= 5
                invalidate()
            } else {
                iterator.remove()
            }

            paintBitmap.alpha = cookie.alpha
            paintText.alpha = cookie.alpha
            canvas.drawBitmap(
                cookieBitmap,
                cookie.point.x - cookieBitmap.width / 2,
                cookie.point.y - cookieBitmap.height / 2,
                paintBitmap
            )
            canvas.drawText(
                "+1",
                cookie.point.x,
                cookie.point.y + cookieBitmap.height / 3 + 4.dpToPx(),
                paintText
            )
        }
    }

    data class CookieInfo(val point: PointF, var alpha: Int = 255)
}