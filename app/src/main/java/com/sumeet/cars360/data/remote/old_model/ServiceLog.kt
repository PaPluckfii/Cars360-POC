package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceLog(
    var _id: String?,
    var status: String?,
    var assignedEmployeeId: String?,
    var assignedEmployeeName: String?,
    var assignedEmployeeNumber: String?,
    var customerMobileNumber: String?,
    var customerId: String?,
    var customerName: String?,
    var car: ServiceCar?,
    var accessoriesList: List<String>?,
    var accessoriesRemark: String?,
    var picturesAtArrival: ServiceLogPictures?,
    var picturesAtDeparture: ServiceLogPictures?,
    var billing: Billing?,
    var estimatedCompletionDateAndTime: String?,
    var dateAndTimeOfCompletion: String?,
    var dateAndTimeOfArrival: String?,
    var services: List<Service>?,
    var estimates: List<Estimate>?,
    var createdAt: String?,
    var modifiedAt: String?
): Parcelable

@Parcelize
data class ServiceCar(
    var carId: String? = null,
    var carBrand: String? = null,
    var carModel: String? = null,
    var bodyColor: String? = null,
    var plateNo: String? = null,
    var fuelType: String? = null,
    var insuranceCompany: String? = null,
    var insuranceExpiryDate: String? = null
): Parcelable