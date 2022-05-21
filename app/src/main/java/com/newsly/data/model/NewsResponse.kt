package com.newsly.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class NewsResponse(

    @field:SerializedName("totalResults") val totalResults: Int? = null,
    @field:SerializedName("articles") val articles: List<ArticlesItem>,
    @field:SerializedName("status") val status: String? = null

)

@Parcelize
data class ArticlesItem(

    @field:SerializedName("publishedAt") val publishedAt: String? = null,
    @field:SerializedName("author") val author: @RawValue Any? = null,
    @field:SerializedName("urlToImage") val urlToImage: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("source") val source: @RawValue Source,
    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("content") val content: String? = null

) : Parcelable

data class Source(

    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("id") val id: String? = null

)
