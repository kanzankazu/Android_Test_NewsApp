package com.salt.feature.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.salt.core.base.BaseActivityBindingView
import com.salt.core.ext.use
import com.salt.feature.R
import com.salt.feature.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivityBindingView<ActivityDetailBinding>() {

    val viewModel by viewModels<DetailViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityDetailBinding
        get() = ActivityDetailBinding::inflate

    override fun setOptionMenu(): Int {
        return R.menu.detail_menu
    }

    override fun setOptionMenuListener(id: Int, item: MenuItem) {
        when (id) {
            R.id.detail_menu_logout -> {
                shareUrl()
            }
        }
    }

    private fun shareUrl() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, viewModel.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun getBundleData() = intent.use {
        viewModel.url = getStringExtra(URL) ?: ""
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setContent() = binding.use {
        ablActivityDetail
        setSupportActionBar(tbActivityDetail)
        tbActivityDetail.setNavigationOnClickListener { onBackPressed() }

        binding.wvActivityDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val requestUrl = request.url.toString()
                view.loadUrl(requestUrl)
                return false
            }
        }
        wvActivityDetail.apply {
            settings.loadsImagesAutomatically = true
            settings.javaScriptEnabled = true
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            loadUrl(viewModel.url)
        }
    }

    companion object {
        const val URL = "URL"
    }
}