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

    fun readCustomerLoginType(): CustomerLoginType{
        return when(preferences?.getInt("CUSTOMER_LOGIN_TYPE",1)){
            1 -> CustomerLoginType.LoggedIn
            2 -> CustomerLoginType.SkippedLogin
            3 -> CustomerLoginType.TakeATour
            else -> CustomerLoginType.TakeATour
        }
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
        return preferences?.getString("MOBILE","")
    }

    fun readUserId() : String? {
        return preferences?.getString("USER_ID","")
    }

    fun readFirebaseId(): String? {
        return preferences?.getString("FIREBASE_ID","")
    }

    fun readUserName() : String? {
        return preferences?.getString("USER_NAME","")
    }

    fun readUserEmail() : String? {
        return preferences?.getString("USER_EMAIL","")
    }

    fun readUserAddress() : String? {
        return preferences?.getString("USER_ADDRESS","")
    }

    fun readUserCity() : String? {
        return preferences?.getString("USER_CITY","")
    }

    fun readUserState() : String? {
        return preferences?.getString("USER_STATE","")
    }

    fun readUserCountry() : String? {
        return preferences?.getString("USER_COUNTRY","")
    }

    fun readUserPostalCode() : String? {
        return preferences?.getString("USER_PCODE","")
    }

    fun readUserDob() : String? {
        return preferences?.getString("USER_DOB","")
    }

    fun readUserGstin() : String? {
        return preferences?.getString("USER_GSTIN","")
    }

    fun readProfileImage() : String? {
        return preferences?.getString("USER_IMAGE","")
    }

    fun readUserDom() : String? {
        return preferences?.getString("USER_IMAGE","")
    }

    fun readSavedCarModelId(): String? {
        return preferences?.getString("CAR_MODEL_ID","")
    }

}