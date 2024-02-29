package com.salt.data.api.news.repository

import com.salt.core.base.baseresponse.BaseResponse
import com.salt.data.api.news.model.TopHeadline

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int, category: String, pageSize: Int, country: String): BaseResponse<Pair<List<TopHeadline.Article>, Int>>
}
