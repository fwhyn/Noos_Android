package com.fwhyn.app.noos.feature.func.news.di

import com.fwhyn.app.noos.BuildConfig
import com.fwhyn.lib.baze.retrofit.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.HttpUrl
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
class RetrofitForNewsModule {

    @Qualifier
    annotation class NewsBaseApi

    @Provides
    @NewsBaseApi
    fun provideBaseUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("newsapi.org")
            .addPathSegment("v2")
            .build()
    }

    @Provides
    @NewsBaseApi
    fun provideApiKey(): () -> String {
        return { "" }
    }

    @Provides
    @NewsBaseApi
    fun provideRetrofit(
        @NewsBaseApi baseUrl: HttpUrl,
        @NewsBaseApi onGetKey: (() -> String)?,
    ): Retrofit {
        val builder = RetrofitBuilder(baseUrl)

        onGetKey?.let {
            builder.addBearerAuth(onGetKey = it)
        }

        if (BuildConfig.DEBUG) {
            builder.enableLog()
        }

        return builder.build()
    }
}