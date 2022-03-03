package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "car_brands")
data class CarBrandEntity(
    @PrimaryKey(autoGenerate = false)
    var brandId: String,
    var brandName: String?,
    var brandLogo: String?
) : Serializable