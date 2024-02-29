package com.salt.feature.navigator

import android.content.Context
import android.content.Intent

interface DetailNavigation {
    fun createIntent(context: Context, url: String): Intent
}