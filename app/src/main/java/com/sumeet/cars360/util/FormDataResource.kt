package com.sumeet.cars360.util

sealed class FormDataResource<T>(val data: T?, val message : String?) {
    class Success<T>(data: T?) : FormDataResource<T>(data,null)
    class Error<T>(message: String?) : FormDataResource<T>(null,message)
    class Loading<T> : FormDataResource<T>(null,"loading data")
}