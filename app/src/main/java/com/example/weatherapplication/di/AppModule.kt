package com.example.weatherapplication.di

import com.example.weatherapplication.network.ApiService
import com.example.weatherapplication.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitClient().buildService(ApiService::class.java)
    }

}