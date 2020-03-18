package com.clickerhunt.cookieclicker.settings

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.clickerhunt.cookieclicker.database.AppDatabase

object SettingsManager {

    private var vibrationIsOn = false
    private lateinit var vibrator: Vibrator

    fun initialize(context: Context) {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val subscribe = AppDatabase.instance.configurationDao().getConfigurationRx().subscribe {
            vibrationIsOn = it.vibrationIsOn
        }
    }

    fun vibrate() {
        vibrate(150L)
    }

    fun vibrateShort() {
        vibrate(32L)
    }

    private fun vibrate(duration: Long) {
        if (vibrationIsOn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        duration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(duration)
            }
        }
    }
}