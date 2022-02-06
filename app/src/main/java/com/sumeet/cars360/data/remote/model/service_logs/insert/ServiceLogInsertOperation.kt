package com.sumeet.cars360.data.remote.model.service_logs.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ServiceLogInsertOperation(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var serviceLogInsertResponse: List<ServiceLogInsertResponse>?
) : Parcelable