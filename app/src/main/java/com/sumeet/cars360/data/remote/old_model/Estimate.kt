package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Estimate(
    var serviceName: String?,
    var estimatedCost: String?
): Parcelable