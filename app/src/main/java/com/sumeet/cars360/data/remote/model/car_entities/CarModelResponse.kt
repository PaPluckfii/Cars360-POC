package com.sumeet.cars360.data.remote.model.car_entities


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CarModelResponse(
    @SerializedName("ModelId")
    var modelId: String?,
    @SerializedName("ModelImage")
    var modelImage: String?,
    @SerializedName("ModelName")
    var modelName: String?
) : Parcelable