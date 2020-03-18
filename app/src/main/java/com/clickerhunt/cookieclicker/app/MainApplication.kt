package com.clickerhunt.cookieclicker.app

import android.app.Application
import com.clickerhunt.cookieclicker.database.AppDatabase

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.invoke(this)
    }
}