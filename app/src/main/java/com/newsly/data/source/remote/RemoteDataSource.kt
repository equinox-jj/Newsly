package com.newsly.data.source.remote

import com.newsly.data.ApiResponse
import com.newsly.data.model.NewsResponse
import com.newsly.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getTopNews(): Flow<ApiResponse<NewsResponse>> {
        return flow {
            val response = apiService.getTopNews()
            emit(ApiResponse.Success(response))
        }.flowOn(Dispatchers.IO)
    }

}