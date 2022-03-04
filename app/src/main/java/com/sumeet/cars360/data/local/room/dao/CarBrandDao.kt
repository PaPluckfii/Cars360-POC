package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sumeet.cars360.data.local.room.model.CarBrandEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarBrandDao {

    @Insert
    suspend fun insertAllCarBrands(brands: List<CarBrandEntity>)

    @Query("SELECT * FROM car_brands")
    fun getAllCarBrands() : Flow<List<CarBrandEntity>>

    @Query("DELETE FROM car_brands")
    suspend fun deleteAllCarBrands()

}