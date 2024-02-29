package com.salt.data.di

import com.salt.data.api.news.repository.NewsRepository
import com.salt.data.dispatcher.IoDispatcher
import com.salt.data.implementation.news.local.preference.NewsPreference
import com.salt.data.implementation.news.remote.api.NewsService
import com.salt.data.implementation.news.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providesDefaultDispatcher(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        newsService: NewsService,
        newsPreference: NewsPreference
    ): NewsRepository = NewsRepositoryImpl(
        coroutineDispatcher,
        newsService,
        newsPreference
    )
}
