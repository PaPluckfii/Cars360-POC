package com.sumeet.cars360.data.remote.model.user


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UsersByFirebaseIdResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var userResponse: List<UserResponse>?
) : Parcelable