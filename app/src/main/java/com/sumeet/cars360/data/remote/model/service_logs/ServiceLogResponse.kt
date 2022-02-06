package com.sumeet.cars360.data.remote.model.service_logs


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ServiceLogResponse(
    @SerializedName("Accessories")
    var accessories: String?,
    @SerializedName("AdditionalDetail")
    var additionalDetail: String?,
    @SerializedName("BackPic")
    var backPic: String?,
    @SerializedName("CarHealthReport")
    var carHealthReport: String?,
    @SerializedName("CarImageId")
    var carImageId: String?,
    @SerializedName("CarServiceId")
    var carServiceId: String?,
    @SerializedName("CreatedDate")
    var createdDate: String?,
    @SerializedName("EstimatedAmount")
    var estimatedAmount: String?,
    @SerializedName("Estimates")
    var estimates: String?,
    @SerializedName("FrontPic")
    var frontPic: String?,
    @SerializedName("FuelIndicator")
    var fuelIndicator: String?,
    @SerializedName("LeftPic")
    var leftPic: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("OriginalAmount")
    var originalAmount: String?,
    @SerializedName("PaidAmount")
    var paidAmount: String?,
    @SerializedName("PaymentMode")
    var paymentMode: String?,
    @SerializedName("ResponseId")
    var responseId: String?,
    @SerializedName("RightPic")
    var rightPic: String?,
    @SerializedName("ServiceTypes")
    var serviceTypes: String?,
    @SerializedName("StatusId")
    var statusId: String?,
    @SerializedName("StatusName")
    var statusName: String?,
    @SerializedName("UserCarRequest")
    var userCarRequest: String?
) : Parcelable