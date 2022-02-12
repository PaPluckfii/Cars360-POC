package com.sumeet.cars360

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarServiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CarServiceApplication.appContext = applicationContext
    }

    companion object {

        lateinit  var appContext: Context

    }
}