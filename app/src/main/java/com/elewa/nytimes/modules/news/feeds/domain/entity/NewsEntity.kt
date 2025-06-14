package com.elewa.nytimes.modules.news.feeds.domain.entity

data class NewsEntity (
    val id: String,
    val title: String?,
    val description: String?,
    val previewUrl: String?,
    val publishedDate: String?
)