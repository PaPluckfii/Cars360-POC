package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    var serviceName: String?,
    var isRequired: String?,
): Parcelable