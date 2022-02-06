package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    var carId: String?,
    var carBrand: String?,
    var carModel: String?,
    var bodyColor: String?,
    var plateNo: String?,
    var fuelType: String?,
    var insuranceCompany: String?,
    var insuranceExpiryDate: String?,
    var frontImage: String?,
    var lastServiceDate: String?,
    var createdAt: String?,
    var modifiedAt: String?
): Parcelable