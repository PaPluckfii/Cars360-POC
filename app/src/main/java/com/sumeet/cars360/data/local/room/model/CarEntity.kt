package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cars")
data class CarEntity(
    @PrimaryKey(autoGenerate = false)
    var carId: String,
    var userId: String?,
    var customerName: String?,
    var carBrand: String?,
    var carBrandLogo: String?,
    var carModelName: String?,
    var carModelLogo: String?,
    var bodyColor: String?,
    var plateNo: String?,
    var fuelType: String?,
    var insuranceCompany: String?,
    var insuranceExpiryDate: String?,
    var frontImage: String?,
    var lastServiceDate: String?,
    var createdAt: String?,
    var modifiedAt: String?
) : Serializable