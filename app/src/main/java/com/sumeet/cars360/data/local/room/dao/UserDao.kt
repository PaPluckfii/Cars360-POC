package com.sumeet.cars360.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sumeet.cars360.data.local.room.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertMyUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = 0")
    fun getMyUser() : Flow<UserEntity>

    @Update
    suspend fun updateMyUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun resetAllUsers()

}