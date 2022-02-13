package com.sumeet.cars360.ui.admin

import com.sumeet.cars360.data.remote.model.user.UserResponse

interface OnAllCustomerItemClickListener {
    fun onAllCustomerItemClicked(userResponse: UserResponse)
}