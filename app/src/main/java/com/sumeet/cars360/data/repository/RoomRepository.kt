package com.sumeet.cars360.data.repository

import com.sumeet.cars360.data.local.room.dao.CarDao
import com.sumeet.cars360.data.local.room.dao.UserDao
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val userDao: UserDao,
    private val carsDao: CarDao
) {
    fun getMyUser() = userDao.getMyUser()

    fun getMyCars() = carsDao.getMyCars()
}