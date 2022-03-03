package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sumeet.cars360.data.local.room.model.ServiceAdvisorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceAdvisorDao {

    @Insert
    suspend fun insertAllServiceAdvisors(serviceAdvisors: List<ServiceAdvisorEntity>)

    @Query("SELECT * FROM service_advisor")
    fun getAllServiceAdvisors(): Flow<List<ServiceAdvisorEntity>>

    @Query("DELETE FROM service_advisor")
    fun deleteALlServiceAdvisors()

}