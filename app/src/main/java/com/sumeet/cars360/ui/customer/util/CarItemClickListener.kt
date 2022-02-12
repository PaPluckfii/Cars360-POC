package com.sumeet.cars360.ui.customer.util

import com.sumeet.cars360.data.remote.model.user_cars.CarDetailsResponse

interface CarItemClickListener {
    fun onCarItemClicked(carDetailsResponse: CarDetailsResponse)
}