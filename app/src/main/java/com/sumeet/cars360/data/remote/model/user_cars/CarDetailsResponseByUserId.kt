package com.sumeet.cars360.data.remote.model.user_cars


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CarDetailsResponseByUserId(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var carDetailsResponse: List<CarDetailsResponse>?
) : Parcelable