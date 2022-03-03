package com.sumeet.cars360.ui.admin.util

import com.sumeet.cars360.data.local.room.model.CustomerEntity

interface CustomerEntityClickListener {
    fun onCustomerEntityItemClicked(customerEntity: CustomerEntity)
}