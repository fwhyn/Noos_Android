package com.fwhyn.app.noos.feature.func.news.data.remote

import com.fwhyn.app.noos.feature.func.news.data.model.NewsResponse
import com.fwhyn.app.noos.feature.func.news.data.model.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    /* --- /v2/top-headlines ------------------------------------ */
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null, // e.g. "us", "jp"
        @Query("category") category: String? = null, // "technology", "business"
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int? = null, // 1-100
        @Query("page") page: Int? = null,
    ): NewsResponse

    /* --- /v2/everything --------------------------------------- */
    @GET("everything")
    suspend fun searchEverything(
        @Query("q") query: String,
        @Query("searchIn") searchIn: String? = null, // title,description,content
        @Query("sources") sources: String? = null,
        @Query("domains") domains: String? = null,
        @Query("from") from: String? = null, // ISO-8601 date
        @Query("to") to: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null, // relevancy,popularity,publishedAt
        @Query("pageSize") pageSize: Int? = null, // 1-100
        @Query("page") page: Int? = null,
    ): NewsResponse

    /* --- /v2/top-headlines/sources ---------------------------- */
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String? = null,
        @Query("language") language: String? = null,
        @Query("country") country: String? = null,
    ): SourcesResponse
}