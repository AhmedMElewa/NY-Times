package com.elewa.nytimes.core.data.soruce.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elewa.nytimes.modules.news.feeds.data.dto.NewsDto
import com.elewa.nytimes.modules.news.feeds.data.source.local.NewsDao

@Database(entities = [NewsDto::class,], version = 1, exportSchema = false)
abstract class NYNewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

const val NY_NEWS_TABLE = "ny_news"