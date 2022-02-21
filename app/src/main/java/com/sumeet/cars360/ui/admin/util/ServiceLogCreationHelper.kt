package com.sumeet.cars360.ui.admin.util

import java.io.File

data class PicturesDTO(
    var leftPic: File? = null,
    var rightPic: File? = null,
    var backPic: File? = null,
    var frontPic: File? = null,
    var odometer: File? = null
)

object CurrentPics{
    val picturesDTO = PicturesDTO()
    var currentImageIndex = 0
}

data class ServiceLogDTO(
    var carId: String = "",
    var accessories: String = "",
    var serviceTypes: String = "",
    var estimates: String = "",
    var additionalDetails: String = "",
    var userCarRequests: String = "",
    var originalAmount: String = "",
    var estimatedAmount: String = "",
    var paidAmount: String = "",
    var paymentMode: String = "",
    var createdBy: String = ""
)

object ServiceLogCreationHelper{
    val serviceLogDTO = ServiceLogDTO()
}