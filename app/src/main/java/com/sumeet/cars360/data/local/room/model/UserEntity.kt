package com.sumeet.cars360.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var responseId: String?,
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
)