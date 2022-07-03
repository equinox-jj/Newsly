package com.newsly.data.repository

import com.newsly.data.ApiResource
import com.newsly.data.model.NewsResponse
import com.newsly.data.source.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    fun getTopNews(): Flow<ApiResource<NewsResponse>> = flow {
        try {
            emit(ApiResource.Loading())
            val response = remoteDataSource.getTopNews()
            emit(ApiResource.Success(response))
        } catch (e: HttpException) {
            emit(ApiResource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(ApiResource.Error(e.localizedMessage ?: "Check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)

}