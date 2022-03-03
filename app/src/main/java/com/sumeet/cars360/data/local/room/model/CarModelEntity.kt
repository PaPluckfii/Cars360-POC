package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "car_models")
data class CarModelEntity(
    @PrimaryKey(autoGenerate = false)
    var modelId: String,
    var modelName: String?,
    var modelLogo: String?,
    var brandId: String?
) : Serializable