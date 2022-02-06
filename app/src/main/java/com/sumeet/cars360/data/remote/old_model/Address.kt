package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var addressLine1: String?,
    var addressLine2: String?,
    var city: String?,
    var state: String?,
    var postalCode: Int?
): Parcelable