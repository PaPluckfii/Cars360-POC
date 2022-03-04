package com.sumeet.cars360.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.sumeet.cars360.CarServiceApplication
import dagger.hilt.android.internal.Contexts
import java.text.SimpleDateFormat

object Constants {

    const val BASE_URL = "https://rmcg.in/V1/API/"

    const val BACK_BUTTON_TIME_INTERVAL = 2000L

    const val AUTH_LOGS = "AUTH_LOGS"

    const val BACK_BUTTON = 16908332

    val MY_DATE_FORMATTER = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

    const val NO_INTERNET_CONNECTION = "No Internet Connection"

    fun hasInternetConnection():Boolean{
        val connectivityManager = Contexts.getApplication(CarServiceApplication.appContext).getSystemService(
            Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork ?: return false
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false;
        }
    }


}