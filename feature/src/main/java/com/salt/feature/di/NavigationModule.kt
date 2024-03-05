@file:OptIn(ExperimentalMaterialApi::class)

package com.salt.feature.di

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import com.salt.core.ext.makeIntent
import com.salt.feature.navigator.DetailNavigation
import com.salt.feature.navigator.MainNavigation
import com.salt.feature.ui.detail.DetailActivity
import com.salt.feature.ui.main.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideLoginRegisterNavigation(
    ) = object : MainNavigation {
        override fun createIntentMainActivity(context: Context) =
            context.makeIntent<MainActivity>()
    }

    @Provides
    fun provideDetailNavigation(
    ) = object : DetailNavigation {
        override fun createIntent(context: Context, url: String) =
            context.makeIntent<DetailActivity>().apply {
                putExtra(DetailActivity.URL, url)
            }
    }
}