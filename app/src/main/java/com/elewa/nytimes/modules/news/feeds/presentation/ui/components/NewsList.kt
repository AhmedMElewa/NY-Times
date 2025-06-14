package com.elewa.nytimes.modules.news.feeds.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity

@Composable
fun NewsList(news: List<NewsEntity>, onNewsClick: (NewsEntity) -> Unit,) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = news,
            key = { item -> item.id } // Important for stable IDs
        ) { item ->
            NewsCard(item = item, onNewsClick = {onNewsClick(item)})
        }
    }
}