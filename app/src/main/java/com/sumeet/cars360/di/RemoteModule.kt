package com.sumeet.cars360.di

import com.sumeet.cars360.data.remote.ApiClient
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideRemoteApi() : ApiClient = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)

    @Singleton
    @Provides
    fun provideRemoteRepository(apiClient: ApiClient) : RemoteRepository = RemoteRepository(apiClient)

}