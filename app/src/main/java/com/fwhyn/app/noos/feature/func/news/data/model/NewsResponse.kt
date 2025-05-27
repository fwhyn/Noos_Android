package com.fwhyn.app.noos.feature.func.news.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val total: Int,
    @SerializedName("articles") val articles: List<Article>,
)