package com.newsly.ui.home

import com.newsly.data.model.NewsResponse

data class HomeState(
    val isLoading: Boolean = false,
    val data: NewsResponse? = null,
    val error: String = "",
)