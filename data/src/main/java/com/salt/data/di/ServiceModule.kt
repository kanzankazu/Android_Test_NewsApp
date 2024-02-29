package com.salt.data.di

import com.salt.data.implementation.news.remote.api.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit,
    ): NewsService = retrofit.create(NewsService::class.java)
}
