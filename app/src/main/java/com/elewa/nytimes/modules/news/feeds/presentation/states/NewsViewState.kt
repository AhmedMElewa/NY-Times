package com.elewa.nytimes.modules.news.feeds.presentation.states

import androidx.annotation.StringRes
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity

data class NewsViewState(
    val isLoading: Boolean = false,
    val news: List<NewsEntity> = emptyList(),
    val selectedNews: NewsEntity? = null,
    @StringRes val error: Int? = null
)