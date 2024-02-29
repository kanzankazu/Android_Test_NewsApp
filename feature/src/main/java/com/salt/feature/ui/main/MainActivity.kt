package com.salt.feature.ui.main

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.salt.core.base.BaseActivityBindingView
import com.salt.core.ext.addFragment
import com.salt.core.ext.use
import com.salt.feature.R
import com.salt.feature.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivityBindingView<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setContent() = binding.use {
        ablActivityMain
        tbActivityMain.title = getString(R.string.label_simple_news_app)
        fcvActivityMain.addFragment(this@MainActivity.supportFragmentManager, MainFragment(), "MainFragment")
    }
}