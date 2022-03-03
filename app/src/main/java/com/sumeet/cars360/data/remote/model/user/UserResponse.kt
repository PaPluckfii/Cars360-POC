package com.sumeet.cars360.data.remote.model.user


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.sumeet.cars360.data.local.room.model.CustomerEntity
import com.sumeet.cars360.data.local.room.model.ServiceAdvisorEntity

@Parcelize
data class UserResponse(
    @SerializedName("ResponseId")
    var responseId: String?,
    @SerializedName("FirebaseId")
    var firebaseId: String?,
    @SerializedName("UserTypeId")
    var userTypeId: String?,
    @SerializedName("UserTypeName")
    var userTypeName: String?,
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Mobile")
    var mobile: String?,
    @SerializedName("Email")
    var email: String?,
    @SerializedName("Address")
    var address: String?,
    @SerializedName("City")
    var city: String?,
    @SerializedName("State")
    var state: String?,
    @SerializedName("Country")
    var country: String?,
    @SerializedName("PostalCode")
    var postalCode: String?,
    @SerializedName("DOB")
    var dOB: String?,
    @SerializedName("DOM")
    var dOM: String?,
    @SerializedName("GSTIN")
    var gSTIN: String?,
    @SerializedName("ProfileImage")
    var profileImage: String?,
    @SerializedName("Message")
    var message: String?
) : Parcelable {

    fun toUserEntity(): CustomerEntity? {
        return responseId?.let {
            CustomerEntity(
                userId = it,
                firebaseId = firebaseId,
                userType = userTypeName,
                name = name,
                mobileNumber = mobile,
                emailId = email,
                address = address,
                city = city,
                state = state,
                postalCode = postalCode,
                dob = dOB,
                dom = dOM,
                gstIn = gSTIN,
                userImageUrl = profileImage
            )
        }
    }

    fun toServiceAdvisorEntity(): ServiceAdvisorEntity? {
        return responseId?.let {
            ServiceAdvisorEntity(
                userId = it,
                firebaseId = firebaseId,
                userType = userTypeName,
                name = name,
                mobileNumber = mobile,
                emailId = email,
                address = address,
                city = city,
                state = state,
                postalCode = postalCode,
                dob = dOB,
                dom = dOM,
                gstIn = gSTIN,
                userImageUrl = profileImage
            )
        }
    }

}