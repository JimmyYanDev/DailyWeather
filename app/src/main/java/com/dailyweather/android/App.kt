package com.dailyweather.android

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        const val TOKEN = "Wl6aSYjcLSn9GoWm"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}