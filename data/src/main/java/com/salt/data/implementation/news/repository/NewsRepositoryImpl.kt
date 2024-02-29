package com.salt.data.implementation.news.repository

import com.salt.core.base.baseresponse.toBaseResponseError
import com.salt.core.base.baseresponse.toBaseResponseSuccess
import com.salt.core.ext.useReturn
import com.salt.data.api.news.repository.NewsRepository
import com.salt.data.implementation.news.local.preference.NewsPreference
import com.salt.data.implementation.news.mapper.toTopHeadline
import com.salt.data.implementation.news.remote.api.NewsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val newsService: NewsService,
    private val newsPreference: NewsPreference,
) : NewsRepository {
    override suspend fun getTopHeadlines(category: String, country: String, page: Int, pageSize: Int) = withContext(ioDispatcher) {
        return@withContext try {
            newsService.topHeadlines(
                category = category,
                country=country,
                page = page,
                pageSize = pageSize,
            ).toTopHeadline().useReturn {
                if (status.equals("ok", true)) Pair(this.articles, totalResults).toBaseResponseSuccess()
                else this.message.toBaseResponseError()
            }
        } catch (e: Exception) {
            e.toBaseResponseError()
        }
    }
}