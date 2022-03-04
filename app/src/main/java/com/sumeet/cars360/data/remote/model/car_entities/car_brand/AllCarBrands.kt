package com.sumeet.cars360.data.remote.model.car_entities.car_brand


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AllCarBrands(
    @SerializedName("BrandId")
    var brandId: String?,
    @SerializedName("BrandLogo")
    var brandLogo: String?,
    @SerializedName("BrandName")
    var brandName: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ResponseId")
    var responseId: String?
) : Parcelable