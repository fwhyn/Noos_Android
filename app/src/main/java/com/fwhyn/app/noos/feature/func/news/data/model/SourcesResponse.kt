package com.fwhyn.app.noos.feature.func.news.data.model

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    override val status: String,
    override val code: String,
    override val message: String,
    @SerializedName("sources") val sources: List<NewsSource>,
) : BasicResponse
