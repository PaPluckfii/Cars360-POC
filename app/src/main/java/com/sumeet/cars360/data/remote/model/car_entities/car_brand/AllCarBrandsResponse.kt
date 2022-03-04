package com.sumeet.cars360.data.remote.model.car_entities.car_brand


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AllCarBrandsResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var allCarBrands: List<AllCarBrands>?
) : Parcelable