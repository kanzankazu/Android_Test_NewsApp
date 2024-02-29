package com.salt.feature.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salt.core.base.baseresponse.BaseResponse
import com.salt.core.base.baseresponse.initBaseResponseLoading
import com.salt.core.ext.getLaunch
import com.salt.core.ext.set
import com.salt.core.ext.toLiveData
import com.salt.data.api.news.model.TopHeadline
import com.salt.data.api.news.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    val categories = arrayListOf("technology", "science", "business", "entertainment", "general", "health", "sports")
    var categorySelected = categories.first()
    var countrySelected = "id"
    var isLoadMore = false
    var currentPage = 1
    var totalPage: Int = 0
    private val limitPerPage = 10

    private val _getTopHeadlines = MutableLiveData<BaseResponse<Pair<List<TopHeadline.Article>, Int>>>()
    val getTopHeadlines = _getTopHeadlines.toLiveData()
    fun getTopHeadlines() {
        _getTopHeadlines.set(initBaseResponseLoading())
        getLaunch {
            _getTopHeadlines.postValue(newsRepository.getTopHeadlines(currentPage, categorySelected, limitPerPage, countrySelected))
        }
    }
}
