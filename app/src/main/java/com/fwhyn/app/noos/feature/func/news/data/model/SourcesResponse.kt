package com.fwhyn.app.noos.feature.func.news.data.model

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("sources") val sources: List<NewsSource>,
)
