package com.elewa.flikerphotos.modules.images.data.mapper

import com.elewa.nytimes.modules.news.feeds.data.dto.NewsDto
import com.elewa.nytimes.modules.news.feeds.data.model.Article

fun Article.toDto(): NewsDto {
    return NewsDto(
        id = this.uri ?: "", // Using URI as unique ID
        title = this.title,
        description = this.abstract,
        previewUrl = this.media?.firstOrNull()?.mediaMetadata
            ?.find { it.format == "mediumThreeByTwo440" }?.url?: this.media?.firstOrNull()?.mediaMetadata
            ?.find { it.format == "mediumThreeByTwo210" }?.url?: this.media?.firstOrNull()?.mediaMetadata
            ?.find { it.format == "Standard Thumbnail" }?.url,
        publishedDate = this.published_date
    )
}

fun List<Article>.toDto(): List<NewsDto> {
    return this.map { it.toDto() }
}