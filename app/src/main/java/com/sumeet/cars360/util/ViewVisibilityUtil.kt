package com.sumeet.cars360.util

import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object ViewVisibilityUtil {

    fun invisible(v: View) {
        v.visibility = View.INVISIBLE
    }

    fun visible(v: View) {
        v.visibility = View.VISIBLE
    }

    fun gone(v: View) {
        v.visibility = View.GONE
    }

    fun visibilityExchanger(visible: View,gone: View){
            visible.visibility = View.VISIBLE
            gone.visibility = View.GONE
    }
}
class SystemUIHandler(private val window: Window,private val view: View) {

    fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, view).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, view).let { controller ->
            controller.show(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}