package com.sumeet.cars360.di

import android.content.Context
import androidx.room.Room
import com.sumeet.cars360.data.local.Cars360RoomDatabase
import com.sumeet.cars360.data.local.room.dao.CarDao
import com.sumeet.cars360.data.local.room.dao.UserDao
import com.sumeet.cars360.data.remote.ApiClient
import com.sumeet.cars360.data.repository.AuthRepository
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.data.repository.RoomRepository
import com.sumeet.cars360.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepository()

}