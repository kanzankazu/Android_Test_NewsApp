package com.salt.feature.ui.main

import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.salt.core.base.BaseActivityBindingCompose
import com.salt.core.compose.ui.theme.BaseTheme
import com.salt.feature.R
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : BaseActivityBindingCompose() {

    private val viewModel by viewModels<MainViewModel>()

    @Composable
    override fun SetContentCompose() {
        BaseTheme {
            MainScreen(getString(R.string.label_simple_news_app), viewModel)
        }
    }
}