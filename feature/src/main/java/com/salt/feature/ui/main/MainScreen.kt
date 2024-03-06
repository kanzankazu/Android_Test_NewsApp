@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)

package com.salt.feature.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kanzankazu.kanzanwidget.compose.extension.initMod
import com.salt.core.compose.ui.Accent
import com.salt.core.compose.ui.Primary
import com.salt.core.compose.ui.PrimaryDark
import com.salt.core.compose.ui.nunitoFamily
import com.salt.core.ext.DateTimeCalendarFormat
import com.salt.core.ext.toNewFormat
import com.salt.data.api.news.model.TopHeadline

@Composable
fun CategoryList(datas: List<String>, onItemClick: (String) -> Unit = {}) {
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        content = {
            itemsIndexed(items = datas) { i, s ->
                Card(
                    modifier = initMod(),
                    shape = RoundedCornerShape(100.dp),
                    onClick = {
                        selectedIndex = i
                        onItemClick.invoke(s)
                    }
                ) {
                    Box(
                        modifier = initMod()
                            .background(
                                brush = Brush.verticalGradient(
                                    if (selectedIndex != i) listOf(PrimaryDark, Primary, Accent)
                                    else listOf(Accent, Accent, Accent)
                                )
                            )
                    ) {
                        Text(
                            color = Color.White,
                            fontFamily = nunitoFamily,
                            fontWeight = FontWeight.Bold,
                            text = s,
                            modifier = initMod().padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    )
}

internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}

@Composable
fun ContentList(
    datas: List<TopHeadline.Article>,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    loadMore: () -> Unit = {},
    listener: (TopHeadline.Article) -> Unit = {},
) {
    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom() } }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && !loading) loadMore()
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        content = {
            itemsIndexed(datas) { i, article ->
                ContentItem(i, article, listener)
            }
            if (loading) {
                item {
                    ContentLoading()
                }
            }
        }
    )
}

@Composable
fun ContentItem(i: Int, article: TopHeadline.Article, listener: (TopHeadline.Article) -> Unit = {}) {
    if (i == 0 && article.urlToImage.isNotEmpty()) MainItemBig(article, listener)
    else MainItemSmall(article, listener)
}

@ExperimentalGlideComposeApi
@Composable
fun MainItemBig(article: TopHeadline.Article, listener: (TopHeadline.Article) -> Unit = {}) {
    Card(
        modifier = initMod()
            .padding(horizontal = 8.dp)
            .shadow(
                4.dp,
                ambientColor = PrimaryDark,
                spotColor = PrimaryDark
            ),
        onClick = { listener.invoke(article) }
    ) {
        Column(
            modifier = initMod()
        ) {
            GlideImage(
                modifier = initMod()
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop,
                model = article.urlToImage,
                contentDescription = ""
            )
            Text(
                modifier = initMod()
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp),
                text = article.publishedAt.toNewFormat(
                    DateTimeCalendarFormat.YYYY_MM_DD_T_HH_MM_SS_Z_.format,
                    DateTimeCalendarFormat.D_MMMM_YYYY.format
                ),
                fontFamily = nunitoFamily,
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )
            Text(
                modifier = initMod()
                    .padding(start = 8.dp)
                    .padding(vertical = 8.dp),
                text = article.title,
                fontFamily = nunitoFamily,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
fun MainItemSmall(article: TopHeadline.Article, listener: (TopHeadline.Article) -> Unit = {}) {
    Card(
        modifier = initMod()
            .padding(horizontal = 8.dp)
            .shadow(
                4.dp,
                ambientColor = PrimaryDark,
                spotColor = PrimaryDark
            ),
        onClick = { listener.invoke(article) }
    ) {
        Row(
            modifier = initMod(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (article.urlToImage.isNotEmpty()) GlideImage(
                modifier = initMod()
                    .width(80.dp)
                    .height(80.dp),
                model = article.urlToImage,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = initMod()
                    .fillMaxSize()
            ) {
                Text(
                    modifier = initMod()
                        .padding(horizontal = (8.dp))
                        .padding(top = 8.dp),
                    text = article.publishedAt.toNewFormat(
                        DateTimeCalendarFormat.YYYY_MM_DD_T_HH_MM_SS_Z_.format,
                        DateTimeCalendarFormat.D_MMMM_YYYY.format
                    ),
                    fontFamily = nunitoFamily,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Text(
                    modifier = initMod()
                        .padding(top = 8.dp, bottom = 8.dp)
                        .padding(horizontal = (8.dp)),
                    text = article.title,
                    fontFamily = nunitoFamily,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun ContentLoading() {
    Card(
        modifier = initMod()
            .padding(8.dp)
            .shadow(
                4.dp,
                ambientColor = PrimaryDark,
                spotColor = PrimaryDark
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = initMod()
                .fillMaxWidth()
        ) {
            CircularProgressIndicator(
                modifier = initMod()
                    .padding(8.dp),
                color = PrimaryDark,
                trackColor = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryListPreview() {
    CategoryList(listOf("kanzan", "kanzan", "kanzan", "kanzan", "kanzan", "kanzan", "kanzan"))
}

@Preview(showBackground = true)
@Composable
fun ContentListPreview() {
    ContentList(
        listOf(
            TopHeadline.Article(author = "verterem", content = "efficitur", description = "semper", publishedAt = "2024-03-04T19:24:05Z", source = TopHeadline.Article.Source(id = "scripta", name = "Cory Ruiz"), title = "Apple Ups Mac Trade-In Prices Following M3 MacBook Air Launch - MacRumors", url = "https://search.yahoo.com/search?p=voluptatibus", urlToImage = "https://images.macrumors.com/t/rqvpVRT0yPDIKtuLcHZu_vF3uJE=/3840x/article-new/2022/03/mac-studio-thumbnail.jpg"),
            TopHeadline.Article(author = "fames", content = "instructior", description = "risus", publishedAt = "2024-03-04T19:24:05Z", source = TopHeadline.Article.Source(id = "lacus", name = "Josefa Horton"), title = "Apple Ups Mac Trade-In Prices Following M3 MacBook Air Launch - MacRumors", url = "https://duckduckgo.com/?q=leo", urlToImage = "https://images.macrumors.com/t/rqvpVRT0yPDIKtuLcHZu_vF3uJE=/3840x/article-new/2022/03/mac-studio-thumbnail.jpg"),
            TopHeadline.Article(author = "qualisque", content = "noluisse", description = "omittantur", publishedAt = "2024-03-04T19:24:05Z", source = TopHeadline.Article.Source(id = "latine", name = "Vince Spears"), title = "Apple Ups Mac Trade-In Prices Following M3 MacBook Air Launch - MacRumors", url = "https://search.yahoo.com/search?p=debet", urlToImage = "https://images.macrumors.com/t/rqvpVRT0yPDIKtuLcHZu_vF3uJE=/3840x/article-new/2022/03/mac-studio-thumbnail.jpg"),
        )
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ContentLoadingPreview() {
    ContentLoading()
}

