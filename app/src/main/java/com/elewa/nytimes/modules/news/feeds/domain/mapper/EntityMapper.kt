package com.elewa.nytimes.modules.news.feeds.domain.mapper

import com.elewa.nytimes.modules.news.feeds.data.dto.NewsDto
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity

fun NewsDto.toDomain(): NewsEntity {
    return NewsEntity(
        id = id,
        title = title,
        description = description,
        previewUrl = previewUrl,
        publishedDate = publishedDate
    )
}

fun List<NewsDto>.toEntity() = map { it.toDomain() }