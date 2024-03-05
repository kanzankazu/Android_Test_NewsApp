package com.salt.data.api.news.repository

import com.salt.core.base.baseresponse.BaseResponse
import com.salt.data.api.news.model.TopHeadline
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(category: String, country: String, page: Int, pageSize: Int): BaseResponse<Pair<List<TopHeadline.Article>, Int>>
    fun getTopHeadlinesFlow(category: String, country: String, page: Int, pageSize: Int): Flow<BaseResponse<Pair<List<TopHeadline.Article>, Int>>>
}
