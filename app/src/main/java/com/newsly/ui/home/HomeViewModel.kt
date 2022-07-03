package com.newsly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsly.data.ApiResource
import com.newsly.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel() {

    private val _newsState = MutableStateFlow(HomeState())
    val newsState: StateFlow<HomeState> = _newsState

    private var retryFunctionList: MutableList<() -> Unit> = mutableListOf()

    init {
        getNewsResponse()
    }

    private fun getNewsResponse() {
        repository.getTopNews().onEach { result ->
            when (result) {
                is ApiResource.Success -> {
                    _newsState.update { previousState ->
                        previousState.copy(
                            isLoading = false,
                            data = result.data
                        )
                    }
                }
                is ApiResource.Error -> {
                    _newsState.update { previousState ->
                        previousState.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred.",
                            data = null
                        )
                    }
                }
                is ApiResource.Loading -> {
                    _newsState.update { previousState ->
                        previousState.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun retryFailed() {
        val currentList = retryFunctionList.toList()
        retryFunctionList.clear()
        currentList.forEach {
            it.invoke()
        }
    }

}