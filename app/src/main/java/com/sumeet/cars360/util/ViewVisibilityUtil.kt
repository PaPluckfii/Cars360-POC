package com.sumeet.cars360.util

import android.view.View

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