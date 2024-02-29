package com.salt.saltandroidtest

import android.app.Application
import com.salt.saltandroidtest.di.AppScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    @AppScope
    lateinit var appCoroutineScope: CoroutineScope

}
