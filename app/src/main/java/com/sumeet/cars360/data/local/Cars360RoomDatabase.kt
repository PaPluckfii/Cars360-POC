package com.sumeet.cars360.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sumeet.cars360.data.local.room.dao.*
import com.sumeet.cars360.data.local.room.model.*

@Database(
    entities = [
        CustomerEntity::class,
        ServiceAdvisorEntity::class,
        CarEntity::class,
        CarBrandEntity::class,
        CarModelEntity::class,
        ServiceLogEntity::class
    ],
    version = 1
)
abstract class Cars360RoomDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun serviceAdvisorDao(): ServiceAdvisorDao
    abstract fun carDao(): CarDao
    abstract fun carBrandDao(): CarBrandDao
    abstract fun carModelDao(): CarModelDao
    abstract fun serviceLogDao(): ServiceLogDao

    companion object {
        const val DATABASE_NAME = "cars360_room_db"
    }

}