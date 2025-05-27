package com.fwhyn.app.noos.feature.func.news.data.model

import com.google.gson.annotations.SerializedName

data class ArticleSource(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String,
)