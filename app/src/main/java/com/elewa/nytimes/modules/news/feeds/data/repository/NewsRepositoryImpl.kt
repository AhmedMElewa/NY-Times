package com.elewa.nytimes.modules.news.feeds.data.repository

import com.elewa.flikerphotos.modules.images.data.mapper.toDto
import com.elewa.nytimes.core.expections.NetworkException
import com.elewa.nytimes.core.expections.ServerException
import com.elewa.nytimes.core.expections.UnknownException
import com.elewa.nytimes.modules.news.feeds.data.source.local.NewsDao
import com.elewa.nytimes.modules.news.feeds.data.source.remote.NewsApiInterface
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity
import com.elewa.nytimes.modules.news.feeds.domain.mapper.toEntity
import com.elewa.nytimes.modules.news.feeds.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remote: NewsApiInterface,
    private val local: NewsDao
): NewsRepository {


    override fun getAllNews(): Flow<List<NewsEntity>> {
        return flow {
            val response1 = remote.getNewsList()

            if (response1.isSuccessful) {
                val news = response1.body()?.results?.toDto() ?: emptyList()
                local.deleteAllNews()
                local.insertAll(news)
            }else{
                when(response1.code()){
                    401 ->{
                        throw NetworkException
                    }
                    501->{
                        throw ServerException
                    }
                    else ->{
                        throw UnknownException
                    }
                }

            }

            emit(local.getAllNews().toEntity())
        }.catch { e ->
            if(e is IOException){
                val list = local.getAllNews().toEntity()
                if (list.isEmpty()){
                    emit(emptyList())
                    throw e
                }else{
                    emit(list)
                }
            }else{
                emit(emptyList())
                throw e
            }

        }.flowOn(Dispatchers.IO)

    }


}