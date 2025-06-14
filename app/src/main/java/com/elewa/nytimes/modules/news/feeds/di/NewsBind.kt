package com.elewa.nytimes.modules.news.feeds.di

import com.elewa.nytimes.modules.news.feeds.data.repository.NewsRepositoryImpl
import com.elewa.nytimes.modules.news.feeds.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class NewsBind {

    @Binds
    abstract fun provideNewsRepos(repository: NewsRepositoryImpl): NewsRepository
}