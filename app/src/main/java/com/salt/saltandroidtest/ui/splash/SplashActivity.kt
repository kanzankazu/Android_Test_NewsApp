package com.salt.saltandroidtest.ui.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.salt.core.base.BaseActivityBindingView
import com.salt.core.ext.PermissionEnumArray
import com.salt.core.ext.addClearTaskNewTask
import com.salt.core.ext.checkPermissions
import com.salt.core.ext.isPermissions
import com.salt.core.ext.resultMultiplePermissions
import com.salt.core.ext.use
import com.salt.core.util.typing
import com.salt.feature.navigator.MainNavigation
import com.salt.saltandroidtest.R
import com.salt.saltandroidtest.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivityBindingView<ActivitySplashBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    private val notificationPermissionResult = resultMultiplePermissions {
        if (it.all { entry -> entry.value }) gotoMain() else finish()
    }

    @Inject
    lateinit var mainNavigation: MainNavigation

    override fun setContent() = binding.use {
        tvActivitySplash.typing(getString(R.string.label_hello_tester), delayMillis = 100) {
            if (isPermissions(PermissionEnumArray.POST_NOTIFICATIONS)) {
                gotoMain()
            } else {
                checkPermissions(PermissionEnumArray.POST_NOTIFICATIONS, notificationPermissionResult)
            }
        }

    }

    private fun gotoMain() {
        val intent = mainNavigation.createIntentMainActivity(this).addClearTaskNewTask()
        startActivity(intent)
    }
}