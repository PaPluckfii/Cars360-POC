package com.sumeet.cars360.data.repository

data class LoginByFirebaseRequest(
    val UserId: String,
    val FirebaseId: String
)

data class CarDetailsByMobileNumberRequest(
    val UserTypeId: String,     //User Type Id for Customer = 3
    val Mobile: String
)

data class GeneralRequest(
    val UserId: String,
    val UserTypeId: String
)

data class CarDetailsRequest(
    val UserId: String,
    val UserTypeId: String,
    val CarId: String
)

data class ServiceStatusChangeRequest(
    val CarServiceId: String,
    val StatusId: String,
    val ModifiedBy: String
)