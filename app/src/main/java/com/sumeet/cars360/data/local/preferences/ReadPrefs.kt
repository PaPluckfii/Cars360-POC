package com.sumeet.cars360.data.local.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class ReadPrefs(mContext : Context) {

    private var preferences : SharedPreferences? = null
    private var savePrefs = "CAR_SERVICE_PREFERENCES"
    private var mode = Activity.MODE_PRIVATE

    init {
        preferences = mContext.getSharedPreferences(savePrefs,mode)
    }

    fun readLoginStatus() : Boolean{
        return preferences?.getBoolean("LOGIN_DATA", false) ?: false
    }

    fun readUserType() : UserType {
        return when(preferences?.getInt("USER_TYPE",1)){
            1 -> UserType.Customer
            2 -> UserType.Employee
            3 -> UserType.Admin
            else -> UserType.Customer
        }
    }

    fun readUserMobileNumber(): String? {
        return preferences?.getString("MOBILE_NUMBER","")
    }

    fun readUserId() : String? {
        return preferences?.getString("USER_ID","")
    }

}