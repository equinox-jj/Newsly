package com.newsly.data.source.remote

import com.newsly.data.model.NewsResponse
import com.newsly.data.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getTopNews(): NewsResponse {
        return apiService.getTopNews()
    }

}