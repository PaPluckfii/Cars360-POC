package com.sumeet.cars360.data.remote.request_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarEntityJsonRequest(
    var UserId: String,
    var ModelId: String,
    var BrandId: String,
    var VehicleNo: String,
    var Color: String,
    var PlateNo: String,
    var FuelType: String,
    var InsuranceCompany: String,
    var ExpiryDate: String,
    var CreatedBy: String
): Parcelable