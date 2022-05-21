package com.newsly.data.network

import com.newsly.data.model.NewsResponse
import com.newsly.utils.Constants.API_KEY
import com.newsly.utils.Constants.QUERY_SOURCES
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("country") sources: String = QUERY_SOURCES,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

}