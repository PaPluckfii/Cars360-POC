package com.sumeet.cars360

import android.app.Application
import android.content.Context
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarServiceApplication : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        CarServiceApplication.appContext = applicationContext
    }

    companion object {
        lateinit  var appContext: Context
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}