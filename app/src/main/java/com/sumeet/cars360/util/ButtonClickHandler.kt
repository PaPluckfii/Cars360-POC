package com.sumeet.cars360.util

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager

object ButtonClickHandler {
    private var mLastClickTime = 0L

    fun buttonClicked(): Boolean{
        if(SystemClock.elapsedRealtime() - mLastClickTime < 500){
            return false
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return true
    }
}

fun hideVirtualKeyBoard(activity: Activity,context: Context){
   activity.currentFocus?.let { view ->
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}