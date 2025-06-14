package com.elewa.nytimes.modules.news.feeds.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elewa.nytimes.modules.news.feeds.data.dto.NewsDto

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<NewsDto>): List<Long>

    @Query("SELECT * FROM ny_news")
    fun getAllNews(): List<NewsDto>

    @Query("DELETE FROM ny_news")
    suspend fun deleteAllNews()

}