package com.newsly.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class NewsResponse(
    @field:SerializedName("articles") val articles: List<ArticlesItem>,
)

@Parcelize
data class ArticlesItem(

    @field:SerializedName("author") val author: String? = null,
    @field:SerializedName("urlToImage") val urlToImage: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("content") val content: String? = null

) : Parcelable
