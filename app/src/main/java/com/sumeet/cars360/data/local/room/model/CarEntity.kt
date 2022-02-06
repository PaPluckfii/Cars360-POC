package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var carId: String?,
    var carBrand: String?,
    var carBrandLogo: String?,
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
)