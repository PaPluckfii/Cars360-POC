package com.sumeet.cars360.data.local.preferences

sealed class UserType{
    object Customer : UserType()
    object Employee : UserType()
    object Admin : UserType()
}

sealed class CustomerLoginType{
    object LoggedIn: CustomerLoginType()
    object SkippedLogin: CustomerLoginType()
    object TakeATour: CustomerLoginType()
}
