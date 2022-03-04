package com.sumeet.cars360.di

import android.content.Context
import androidx.room.Room
import com.sumeet.cars360.data.local.Cars360RoomDatabase
import com.sumeet.cars360.data.local.room.dao.*
import com.sumeet.cars360.data.remote.ApiClient
import com.sumeet.cars360.data.repository.CacheRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideCacheRepository(
        apiClient: ApiClient,
        roomRepository: Cars360RoomDatabase
    ): CacheRepository = CacheRepository(apiClient,roomRepository)

    @Singleton
    @Provides
    fun  provideRoomDatabase(@ApplicationContext context: Context): Cars360RoomDatabase =
        Room.databaseBuilder(
            context,
            Cars360RoomDatabase::class.java,
            Cars360RoomDatabase.DATABASE_NAME
        ).build()

}