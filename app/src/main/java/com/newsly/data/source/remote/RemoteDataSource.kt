package com.newsly.data.source.remote

import com.newsly.data.ApiResponse
import com.newsly.data.model.NewsResponse
import com.newsly.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getTopNews(): Flow<ApiResponse<NewsResponse>> {
        return flow {
            try {
                val response = apiService.getTopNews()
                if (response.articles.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Timber.tag("RemoteDataSource Success").d(response.toString())
                } else {
                    emit(ApiResponse.Empty)
                    Timber.tag("RemoteDataSource Empty").d(ApiResponse.Empty.toString())
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.tag("RemoteDataSource Error").d(e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}