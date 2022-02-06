package com.sumeet.cars360.data.remote.model.car_entities.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ModelInsertOperation(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var modelInsertResponse: List<ModelInsertResponse>?
) : Parcelable