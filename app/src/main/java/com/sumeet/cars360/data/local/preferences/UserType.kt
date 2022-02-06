package com.sumeet.cars360.data.local.preferences

sealed class UserType{
    object Customer : UserType()
    object Employee : UserType()
    object Admin : UserType()
}
