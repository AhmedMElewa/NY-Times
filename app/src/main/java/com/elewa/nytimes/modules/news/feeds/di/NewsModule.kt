package com.elewa.nytimes.modules.news.feeds.di

import com.elewa.nytimes.core.data.soruce.local.NYNewsDatabase
import com.elewa.nytimes.modules.news.feeds.data.source.local.NewsDao
import com.elewa.nytimes.modules.news.feeds.data.source.remote.NewsApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsModule {

    @Provides
    @Singleton
    fun provideNewsModule(retrofit: Retrofit): NewsApiInterface{
        return retrofit.create(NewsApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDaoModule(database: NYNewsDatabase): NewsDao {
        return database.newsDao()
    }

}