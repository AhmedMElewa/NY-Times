package com.elewa.nytimes.modules.news.feeds.presentation.states

import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity

sealed class NewsIntent {
    object LoadNews : NewsIntent()
    data class SelectNews(val news: NewsEntity) : NewsIntent()
}
