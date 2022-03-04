package com.sumeet.cars360.data.wrapper

sealed class UserType{
    object Customer : UserType()
    object Employee : UserType()
    object Admin : UserType()
}

sealed class CustomerLoginType{
    object LoggedIn: CustomerLoginType()        //All Data
    object SkippedLogin: CustomerLoginType()    //Only Car data
    object TakeATour: CustomerLoginType()       //No Data
}
