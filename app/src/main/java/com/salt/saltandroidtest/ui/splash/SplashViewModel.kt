package com.salt.saltandroidtest.ui.splash

import androidx.lifecycle.ViewModel
import com.salt.data.api.news.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel()
