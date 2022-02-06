package com.sumeet.cars360.data.remote.old_model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("ResponseId")
    val userId: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Email")
    var emailId: String?,
    @SerializedName("Mobile")
    var mobileNumber: String?,
    @SerializedName("ResponseId")
    var role: String?,

    @SerializedName("FirebaseId")
    var firebaseId: String?,

    @SerializedName("ResponseId")
    var address: Address?,
    @SerializedName("ResponseId")
    var dob: String?,
    @SerializedName("ResponseId")
    var dom: String?,
    @SerializedName("ResponseId")
    var gstNo: String?,
    @SerializedName("ResponseId")
    var cars: List<Car>?,
    @SerializedName("ResponseId")
    var userImageUrl: String?
): Parcelable
