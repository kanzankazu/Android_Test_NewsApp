package com.salt.feature.ui.main

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanzankazu.kanzanwidget.compose.extension.initMod
import com.salt.core.base.BaseActivityBindingCompose
import com.salt.core.compose.component.topbar.TitleTopBar
import com.salt.core.compose.ui.theme.BaseTheme
import com.salt.data.api.news.model.TopHeadline
import com.salt.feature.R
import com.salt.feature.navigator.DetailNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : BaseActivityBindingCompose() {

    @Inject
    lateinit var detailNavigation: DetailNavigation

    private val viewModel by viewModels<MainViewModel>()

    @Composable
    override fun SetContentCompose() {
        BaseTheme {
            MainScreen(getString(R.string.label_simple_news_app), viewModel) {
                val intent = detailNavigation.createIntent(this, it.url)
                startActivity(intent)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen(title: String, viewModel: MainViewModel = hiltViewModel(), listener: (TopHeadline.Article) -> Unit = {}) {
    var isLoadMore by remember { mutableStateOf(false) }
    val articleBaseState = viewModel.getTopHeadlinesFlow.value
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        viewModel.currentPage = 1
        viewModel.getTopHeadlinesFlow()
    })

    Scaffold(topBar = { TitleTopBar(title) }) {
        Box(
            modifier = initMod()
                .pullRefresh(pullRefreshState)
        ) {
            Column(modifier = initMod().padding(it)) {
                CategoryList(viewModel.categories) {
                    viewModel.currentPage = 1
                    viewModel.categorySelected = it
                    viewModel.getTopHeadlinesFlow()
                }
                ContentList(articleBaseState.list, listener = listener)
            }

            PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}