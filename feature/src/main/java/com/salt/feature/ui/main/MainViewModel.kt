package com.salt.feature.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salt.core.base.BaseState
import com.salt.core.base.baseresponse.BaseResponse
import com.salt.core.base.baseresponse.handleBaseResponse
import com.salt.core.base.baseresponse.initBaseResponseLoading
import com.salt.core.ext.getLaunch
import com.salt.core.ext.set
import com.salt.core.ext.toLiveData
import com.salt.core.ext.toState
import com.salt.data.api.news.model.TopHeadline
import com.salt.data.api.news.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()


    val categories = arrayListOf("technology", "science", "business", "entertainment", "general", "health", "sports")
    var categorySelected = categories.first()
    var countrySelected = "us"
    var isLoadMore = false
    var currentPage = 1
    var totalPage: Int = 0
    val limitPerPage = 10

    private val _getTopHeadlines = MutableLiveData<BaseResponse<Pair<List<TopHeadline.Article>, Int>>>()
    val getTopHeadlines = _getTopHeadlines.toLiveData()
    fun getTopHeadlines() {
        _getTopHeadlines.set(initBaseResponseLoading())
        getLaunch {
            _getTopHeadlines.postValue(newsRepository.getTopHeadlines(categorySelected, countrySelected, currentPage, limitPerPage))
        }
    }

    private val _getTopHeadlinesFlow = mutableStateOf(BaseState<TopHeadline.Article>())
    val getTopHeadlinesFlow = _getTopHeadlinesFlow.toState()
    fun getTopHeadlinesFlow() {
        newsRepository.getTopHeadlinesFlow(categorySelected, countrySelected, currentPage, limitPerPage).onEach { result ->
            result.handleBaseResponse(
                onLoading = { _getTopHeadlinesFlow.value = BaseState(it, emptyList(), "") },
                onEmpty = { _getTopHeadlinesFlow.value = BaseState(false, emptyList(), "") },
                onError = { _getTopHeadlinesFlow.value = BaseState(false, emptyList(), it) },
                onSuccess = { _getTopHeadlinesFlow.value = BaseState(false, it.first, "") }
            )
        }.launchIn(viewModelScope)
    }

    init {
        getTopHeadlinesFlow()
    }
}
