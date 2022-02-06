package com.sumeet.cars360.data.remote.model.user_cars.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CarDetailsInsertOperation(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var carInsertResponse: List<CarInsertResponse>?
) : Parcelable