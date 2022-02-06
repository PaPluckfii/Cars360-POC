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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://rmcg.in/V1/API/"

@Module
@InstallIn(SingletonComponent ::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepository()

    @Singleton
    @Provides
    fun provideRemoteApi() : ApiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)

    @Singleton
    @Provides
    fun provideRemoteRepository(apiClient: ApiClient) : RemoteRepository = RemoteRepository(apiClient)

    @Singleton
    @Provides
    fun provideRoomRepository(
        userDao: UserDao,
        carDao: CarDao
    ): RoomRepository = RoomRepository(userDao,carDao)

    @Singleton
    @Provides
    fun  provideRoomDatabase(@ApplicationContext context: Context): Cars360RoomDatabase =
        Room.databaseBuilder(
            context,
            Cars360RoomDatabase::class.java,
            Cars360RoomDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideUserDao(cars360RoomDatabase: Cars360RoomDatabase): UserDao = cars360RoomDatabase.userDao()

    @Singleton
    @Provides
    fun provideCarDao(cars360RoomDatabase: Cars360RoomDatabase): CarDao = cars360RoomDatabase.carDao()

}