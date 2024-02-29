package com.salt.data.implementation.news.mapper

import com.salt.core.ext.orNull
import com.salt.core.ext.orNullListNotNot
import com.salt.core.ext.orNullObject
import com.salt.data.api.news.model.TopHeadline
import com.salt.data.implementation.news.remote.response.TopHeadlineResponse

fun TopHeadlineResponse.toTopHeadline() = TopHeadline(
    articles = articles.orNullListNotNot(TopHeadline.Article()) {
        it.toArticle()
    },
    status = status.orNull(),
    totalResults = totalResults.orNull(),
    message = message.orNull()
)

private fun TopHeadlineResponse.ArticleResponse.toArticle() = TopHeadline.Article(
    author = author.orNull(),
    content = content.orNull(),
    description = description.orNull(),
    publishedAt = publishedAt.orNull(),
    source = source.orNullObject(TopHeadline.Article.Source()) { it.toSource() },
    title = title.orNull(),
    url = url.orNull(),
    urlToImage = urlToImage.orNull()
)

private fun TopHeadlineResponse.ArticleResponse.SourceResponse.toSource() = TopHeadline.Article.Source(
    id = id.orNull(),
    name = name.orNull()

)
