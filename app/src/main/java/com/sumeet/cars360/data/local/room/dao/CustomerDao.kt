package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sumeet.cars360.data.local.room.model.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCustomers(customers: List<CustomerEntity>)

    @Query("SELECT * FROM customers")
    fun getAllCustomers() : Flow<List<CustomerEntity>>

    @Query("DELETE FROM customers")
    suspend fun deleteAllCustomers()

}