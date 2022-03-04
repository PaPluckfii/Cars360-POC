package com.sumeet.cars360.data.wrapper

sealed class ServiceLogStatus(){
    object PENDING: ServiceLogStatus()
    object READY: ServiceLogStatus()
    object DELIVERED: ServiceLogStatus()
    object CANCELLED: ServiceLogStatus()
    object RE_ESTIMATE: ServiceLogStatus()
}