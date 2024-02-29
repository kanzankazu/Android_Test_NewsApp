package com.salt.data.di

import android.content.Context
import com.salt.data.implementation.news.local.preference.NewsPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    fun provideUserPreference(
        context: Context,
    ) = NewsPreference(context)
}