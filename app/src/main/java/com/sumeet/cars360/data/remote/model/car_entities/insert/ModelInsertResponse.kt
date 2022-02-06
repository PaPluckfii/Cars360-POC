package com.sumeet.cars360.data.remote.model.car_entities.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ModelInsertResponse(
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ModelId")
    var modelId: String?,
    @SerializedName("ResponseId")
    var responseId: String?
) : Parcelable