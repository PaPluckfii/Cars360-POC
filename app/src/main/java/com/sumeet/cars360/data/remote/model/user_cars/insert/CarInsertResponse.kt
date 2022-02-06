package com.sumeet.cars360.data.remote.model.user_cars.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CarInsertResponse(
    @SerializedName("CarId")
    var carId: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ResponseId")
    var responseId: String?
) : Parcelable