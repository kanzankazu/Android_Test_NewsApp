package com.salt.data.implementation.news.remote.api

import com.salt.data.implementation.news.remote.response.TopHeadlineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("https://newsapi.org/v2/top-headlines")
    suspend fun topHeadlines(
        @Query("page") page: Int,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = "856b7de912044191accd12e15175ca78",
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 10,
    ): TopHeadlineResponse
}
