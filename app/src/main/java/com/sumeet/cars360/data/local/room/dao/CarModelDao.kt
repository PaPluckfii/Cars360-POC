package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarModelDao {

    @Insert
    suspend fun insertAllCarModels(models: List<CarModelEntity>)

    @Query("SELECT * FROM car_models WHERE brandId = :brandId")
    fun getAllCarModelsForBrand(brandId: String): Flow<List<CarModelEntity>>

    @Query("DELETE FROM car_models")
    suspend fun deleteAllCarModels()

}