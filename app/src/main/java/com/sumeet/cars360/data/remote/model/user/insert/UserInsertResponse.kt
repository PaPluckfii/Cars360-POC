package com.sumeet.cars360.data.remote.model.user.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserInsertResponse(
    @SerializedName("Message")
    var message: String?,
    @SerializedName("ResponseId")
    var responseId: String?,
    @SerializedName("UserId")
    var userId: String?
) : Parcelable