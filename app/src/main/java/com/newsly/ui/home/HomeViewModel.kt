package com.newsly.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsly.data.ApiResource
import com.newsly.data.model.NewsResponse
import com.newsly.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsResponses = MutableLiveData<ApiResource<NewsResponse>>()
    val newsResponses: LiveData<ApiResource<NewsResponse>> get() = _newsResponses

    private var retryFunctionList: MutableList<() -> Unit> = mutableListOf()

    fun getNewsResponse() {
        viewModelScope.launch {
            repository.getTopNews()
                .onStart {
                    _newsResponses.postValue(ApiResource.Loading())
                }
                .catch {
                    it.message?.let { message ->
                        _newsResponses.postValue(ApiResource.Error(message))
                        retryFunctionList.add(::getNewsResponse)
                    }
                }
                .collect { news ->
                    news.data.let {
                        _newsResponses.postValue(ApiResource.Success(it))
                    }
                }
        }
    }

    fun retryAllFailed() {
        val currentList = retryFunctionList.toList()
        retryFunctionList.clear()
        currentList.forEach {
            it.invoke()
        }
    }

}