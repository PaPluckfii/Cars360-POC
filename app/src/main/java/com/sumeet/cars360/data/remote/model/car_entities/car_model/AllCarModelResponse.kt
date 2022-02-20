package com.sumeet.cars360.data.remote.model.car_entities.car_model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AllCarModelResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var allCarModels: List<AllCarModels>?
) : Parcelable