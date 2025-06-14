package com.elewa.nytimes.modules.news.feeds.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elewa.nytimes.core.data.soruce.local.NY_NEWS_TABLE

@Entity(tableName = NY_NEWS_TABLE)
data class NewsDto(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val title: String?,
    @ColumnInfo
    val description: String?,
    @ColumnInfo(name = "path")
    val previewUrl: String?,
    @ColumnInfo(name = "published_date")
    val publishedDate: String?,
)