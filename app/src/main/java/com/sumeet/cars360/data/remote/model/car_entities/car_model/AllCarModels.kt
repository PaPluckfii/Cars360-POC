package com.sumeet.cars360.data.remote.model.car_entities.car_model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AllCarModels(
    @SerializedName("BrandId")
    var brandId: String?,
    @SerializedName("BrandName")
    var brandName: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ModelId")
    var modelId: String?,
    @SerializedName("ModelImage")
    var modelImage: String?,
    @SerializedName("ModelName")
    var modelName: String?,
    @SerializedName("ResponseId")
    var responseId: String?
) : Parcelable