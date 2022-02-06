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
    suspend fun insertMyCar(carEntity: CarEntity)

    @Query("SELECT * FROM cars")
    fun getMyCars(): Flow<List<CarEntity>>

    @Update
    suspend fun updateMyCar(carEntity: CarEntity)

    @Query("DELETE FROM cars")
    suspend fun resetAllCars()

}