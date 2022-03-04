package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = false)
    var userId: String,
    var firebaseId: String?,
    var userType: String?,
    var name: String?,
    var mobileNumber: String?,
    var emailId: String?,
    var address: String?,
    var city: String?,
    var state: String?,
    var postalCode: String?,
    var dob: String?,
    var dom: String?,
    var gstIn: String?,
    var userImageUrl: String?
) : Serializable