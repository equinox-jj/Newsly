package com.newsly.data.repository

import com.newsly.data.ApiResource
import com.newsly.data.ApiResponse
import com.newsly.data.model.NewsResponse
import com.newsly.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getTopNews(): Flow<ApiResource<NewsResponse>> = flow {
        emit(ApiResource.Loading())
        when (val response = remoteDataSource.getTopNews().first()) {
            is ApiResponse.Success -> {
                val data = response.data
                emit(ApiResource.Success(data))
                Timber.tag("Repository Success").d(data.toString())
            }
            is ApiResponse.Empty -> {
                emit(ApiResource.Success(null))
                Timber.tag("Repository Empty").d(ApiResponse.Empty.toString())
            }
            is ApiResponse.Error -> {
                emit(ApiResource.Error(response.errorMessage))
                Timber.tag("Repository Empty").d(response.errorMessage)
            }
        }
    }

}