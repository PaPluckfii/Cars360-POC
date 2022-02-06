package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cars360ServiceDetails(
    var id: String?,
    var serviceName: String?,
    var type: String?
): Parcelable