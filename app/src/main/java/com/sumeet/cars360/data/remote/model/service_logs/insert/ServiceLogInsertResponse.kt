package com.sumeet.cars360.data.remote.model.service_logs.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ServiceLogInsertResponse(
    @SerializedName("CarServiceId")
    var carServiceId: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ResponseId")
    var responseId: String?
) : Parcelable