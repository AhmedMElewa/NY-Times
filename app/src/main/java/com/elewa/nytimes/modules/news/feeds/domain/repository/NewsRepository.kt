package com.elewa.nytimes.modules.news.feeds.domain.repository

import androidx.paging.ExperimentalPagingApi
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository  {

    fun getAllNews(): Flow<List<NewsEntity>>
}