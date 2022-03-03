package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sumeet.cars360.data.local.room.model.ServiceLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceLogDao {

    @Insert
    suspend fun insertAllLogs(logs: List<ServiceLogEntity>)

    @Query("SELECT * FROM service_logs")
    fun getAllServiceLogs(): Flow<List<ServiceLogEntity>>

    @Query("SELECT * FROM service_logs WHERE statusName = :status")
    fun getALlServiceLogsBasedOnStatus(status: String): Flow<List<ServiceLogEntity>>

    @Query("DELETE FROM service_logs")
    fun deleteAllServiceLogs()

}