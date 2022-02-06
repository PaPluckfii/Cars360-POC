package com.sumeet.cars360.data.remote.model.car_entities


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CarBrandResponse(
    @SerializedName("BrandId")
    var brandId: String?,
    @SerializedName("BrandLogo")
    var brandLogo: String?,
    @SerializedName("BrandName")
    var brandName: String?,
    @SerializedName("Models")
    var carModelResponses: List<CarModelResponse>?
) : Parcelable