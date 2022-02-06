package com.sumeet.cars360.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sumeet.cars360.data.local.room.dao.CarDao
import com.sumeet.cars360.data.local.room.dao.UserDao
import com.sumeet.cars360.data.local.room.model.CarEntity
import com.sumeet.cars360.data.local.room.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CarEntity::class
    ],
    version = 1
)
abstract class Cars360RoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao

    companion object {
        const val DATABASE_NAME = "cars360_room_db"
    }

}