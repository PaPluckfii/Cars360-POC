package com.sumeet.cars360.ui.admin

import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogResponse

interface OnServiceItemClickListener {
    fun onServiceItemClicked(serviceLogResponse: ServiceLogResponse)
}