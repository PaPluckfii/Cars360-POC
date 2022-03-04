package com.sumeet.cars360.data.remote.form_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class ServiceLogFormData(
    var serviceLogId: String = "",
    var carId: String = "",
    var accessories: String = "",
    var serviceTypes: String = "",
    var estimates: String = "",
    var additionalDetails: String = "",
    var userCarRequest: String = "",
    var originalAmount: String = "",
    var estimatedAmount: String = "",
    var paidAmount: String = "",
    var paymentMode: String = "",
    var createdBy: String = "",
    var leftPic: File? = null,
    var rightPic: File? = null,
    var frontPic: File? = null,
    var backPic: File? = null,
    var fuelIndicator: File? = null,
    var carHealthReportFile: File? = null,
): Parcelable