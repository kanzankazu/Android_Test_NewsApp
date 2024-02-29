package com.salt.data.implementation.news.remote.response


import com.google.gson.annotations.SerializedName

data class TopHeadlineResponse(
    @SerializedName("articles") var articles: List<ArticleResponse?>? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("message") var message: String? = null,
) {
    data class ArticleResponse(
        @SerializedName("author") var author: String? = null,
        @SerializedName("content") var content: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("publishedAt") var publishedAt: String? = null,
        @SerializedName("source") var source: SourceResponse? = null,
        @SerializedName("title") var title: String? = null,
        @SerializedName("url") var url: String? = null,
        @SerializedName("urlToImage") var urlToImage: String? = null,
    ) {
        data class SourceResponse(
            @SerializedName("id") var id: String? = null,
            @SerializedName("name") var name: String? = null,
        )
    }
}