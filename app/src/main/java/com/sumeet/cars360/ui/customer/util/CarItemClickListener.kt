package com.sumeet.cars360.ui.customer.util

import com.sumeet.cars360.data.local.room.model.CarEntity

interface CarItemClickListener {
    fun onCarItemClicked(carEntity: CarEntity)
}