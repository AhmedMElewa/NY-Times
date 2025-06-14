package com.elewa.nytimes.modules.news.feeds.data.source.remote

import com.elewa.nytimes.modules.news.feeds.data.model.NYTimesResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiInterface {

    @GET("mostpopular/v2/viewed/7.json")
    suspend fun getNewsList(
    ) : Response<NYTimesResponse>
}