package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "service_logs")
data class ServiceLogEntity(
    @PrimaryKey(autoGenerate = false)
    var serviceLogId: String,
    var statusName: String?,
    var accessories: String?,
    var estimates: String?,
    var additionalDetail: String?,
    var frontPic: String?,
    var backPic: String?,
    var leftPic: String?,
    var rightPic: String?,
    var fuelIndicator: String?,
    var carHealthReport: String?,
    var estimatedAmount: String?,
    var originalAmount: String?,
    var paidAmount: String?,
    var paymentMode: String?,
    var serviceTypes: String?,
    var userCarRequest: String?,
    var createdDate: String?
) : Serializable
