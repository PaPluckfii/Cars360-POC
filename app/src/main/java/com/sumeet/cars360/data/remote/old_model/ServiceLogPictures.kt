package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceLogPictures(
    var backPic: String?,
    var frontPic: String?,
    var fuelIndicator: String?,
    var leftPic: String?,
    var createdAt: String?,
    var rightPic: String?,
    var modifiedAt: String?
): Parcelable