package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Billing(
    var actualCost: Double?,
    var createdAt: String?,
    var isPaid: Boolean?,
    var modifiedAt: String?,
    var originalEstimate: Double?,
    var paymentMode: String?
): Parcelable