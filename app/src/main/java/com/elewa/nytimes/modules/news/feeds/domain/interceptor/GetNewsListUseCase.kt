package com.elewa.nytimes.modules.news.feeds.domain.interceptor


import com.elewa.nytimes.base.BaseUseCase
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity
import com.elewa.nytimes.modules.news.feeds.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor (private val repository: NewsRepository) :
    BaseUseCase<Unit, Flow<List<NewsEntity>>> {

    override suspend fun execute(params: Unit?): Flow<List<NewsEntity>> {
        return repository.getAllNews()
    }
}