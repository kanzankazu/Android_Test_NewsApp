package com.salt.saltandroidtest.ui.splash

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.salt.core.base.BaseActivityBindingCompose
import com.salt.core.compose.ui.theme.BaseTheme
import com.salt.core.ext.PermissionEnumArray
import com.salt.core.ext.addClearTaskNewTask
import com.salt.core.ext.checkPermissions
import com.salt.core.ext.isPermissions
import com.salt.core.ext.resultMultiplePermissions
import com.salt.feature.navigator.MainNavigation
import com.salt.saltandroidtest.R
import com.salt.saltandroidtest.ui.component.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivityBindingCompose() {

    private val notificationPermissionResult = resultMultiplePermissions {
        if (it.all { entry -> entry.value }) gotoMain() else finish()
    }

    @Inject
    lateinit var mainNavigation: MainNavigation

    @Composable
    override fun SetContentCompose() {
        BaseTheme {
            SplashScreen(listOf(getString(R.string.label_hello_tester))) {
                if (isPermissions(PermissionEnumArray.POST_NOTIFICATIONS)) {
                    gotoMain()
                } else {
                    checkPermissions(PermissionEnumArray.POST_NOTIFICATIONS, notificationPermissionResult)
                }
            }
        }
    }

    private fun gotoMain() {
        val intent = mainNavigation.createIntentMainActivity(this).addClearTaskNewTask()
        startActivity(intent)
    }
}