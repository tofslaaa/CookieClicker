package com.clickerhunt.cookieclicker.app

import android.app.Application
import com.clickerhunt.cookieclicker.cookie.CookieManager
import com.clickerhunt.cookieclicker.database.AppDatabase
import com.clickerhunt.cookieclicker.settings.SettingsManager

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.invoke(this)
        CookieManager.initialize()
        CookieManager.start()
        SettingsManager.initialize(this)
    }
}