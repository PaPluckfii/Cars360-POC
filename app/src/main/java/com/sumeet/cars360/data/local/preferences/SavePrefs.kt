package com.sumeet.cars360.data.local.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception

class SavePrefs(mContext: Context) {
    private var preferences: SharedPreferences? = null
    private val savePrefs = "CAR_SERVICE_PREFERENCES"
    private val mode = Activity.MODE_PRIVATE
    private var editor: SharedPreferences.Editor? = null

    init {
        preferences = mContext.getSharedPreferences(savePrefs, mode)
        try {
            editor = preferences?.edit()
        }catch (e: Exception){}
    }

    fun saveLoginStatus(loginStatus: Boolean) {
        editor?.putBoolean("LOGIN_DATA", loginStatus)?.apply()
    }

    fun saveUserType(userType: UserType) {
        editor?.putInt(
            "USER_TYPE",
            when (userType) {
                UserType.Customer -> 1
                UserType.Employee -> 2
                UserType.Admin -> 3
                else -> 1
            }
        )?.apply()
    }

    fun saveUserMobileNumber(mobileNo: String){
        editor?.putString("MOBILE_NUMBER",mobileNo)
    }

    fun saveUserId(userId: String) {
        editor?.putString("USER_ID", userId)?.apply()
    }

    fun saveUserName(userName: String) {
        editor?.putString("USER_NAME", userName)?.apply()
    }

    fun saveUserEmail(userEmail: String) {
        editor?.putString("USER_EMAIL", userEmail)?.apply()
    }

    fun saveUserAddress(userAddress: String) {
        editor?.putString("USER_ADDRESS", userAddress)?.apply()
    }

    fun saveUserCity(userCity: String) {
        editor?.putString("USER_CITY", userCity)?.apply()
    }

    fun saveUserState(userState: String) {
        editor?.putString("USER_STATE", userState)?.apply()
    }

    fun saveUserCountry(userCountry: String) {
        editor?.putString("USER_COUNTRY", userCountry)?.apply()
    }

    fun saveUserPostalCode(userPcode: String) {
        editor?.putString("USER_PCODE", userPcode)?.apply()
    }

    fun saveUserDob(userDob: String) {
        editor?.putString("USER_DOB", userDob)?.apply()
    }

    fun saveUserGstin(userGstin:String){
        editor?.putString("USER_GSTIN", userGstin)?.apply()
    }

    fun saveProfileImage(userImage: String) {
        editor?.putString("USER_IMAGE", userImage)?.apply()
    }

    fun saveUserDom(userDom: String) {
        editor?.putString("USER_DOM", userDom)?.apply()
    }
}