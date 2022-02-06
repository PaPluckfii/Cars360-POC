package com.sumeet.cars360.data.remote.model.user.insert


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserInsertOperation(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("response")
    var userInsertResponse: List<UserInsertResponse>?
) : Parcelable