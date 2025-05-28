package com.fwhyn.app.noos.feature.func.news.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    override val status: String,
    override val code: String,
    override val message: String,
    @SerializedName("totalResults") val total: Int,
    @SerializedName("articles") val articles: List<Article>,
) : BasicResponse