package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sumeet.cars360.data.local.room.model.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert
    suspend fun insertAllCars(carEntities: List<CarEntity>)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<CarEntity>>

    @Query("SELECT * FROM cars WHERE userId = :userId")
    fun getCarsByCustomerId(userId: String): Flow<List<CarEntity>>

    @Query("DELETE FROM cars")
    suspend fun deleteALlCars()

}