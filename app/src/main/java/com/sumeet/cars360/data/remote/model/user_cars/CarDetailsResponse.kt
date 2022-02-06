package com.sumeet.cars360.data.remote.model.user_cars


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CarDetailsResponse(
    @SerializedName("BrandId")
    var brandId: String?,
    @SerializedName("BrandLogo")
    var brandLogo: String?,
    @SerializedName("BrandName")
    var brandName: String?,
    @SerializedName("CarId")
    var carId: String?,
    @SerializedName("Color")
    var color: String?,
    @SerializedName("CreatedDate")
    var createdDate: String?,
    @SerializedName("ExpiryDate")
    var expiryDate: String?,
    @SerializedName("FuelType")
    var fuelType: String?,
    @SerializedName("InsuranceCompany")
    var insuranceCompany: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ModelId")
    var modelId: String?,
    @SerializedName("ModelImage")
    var modelImage: String?,
    @SerializedName("ModelName")
    var modelName: String?,
    @SerializedName("Name")
    var name: String?,
    @SerializedName("PlateNo")
    var plateNo: String?,
    @SerializedName("ResponseId")
    var responseId: String?,
    @SerializedName("UserId")
    var userId: String?,
    @SerializedName("VehicleNo")
    var vehicleNo: String?
) : Parcelable