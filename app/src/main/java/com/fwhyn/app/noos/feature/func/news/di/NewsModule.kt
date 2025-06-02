package com.fwhyn.app.noos.feature.func.news.di

import com.fwhyn.app.noos.feature.func.news.data.remote.ArticlesRemoteDataSource
import com.fwhyn.app.noos.feature.func.news.data.remote.ArticlesRemoteDataSourceImpl
import com.fwhyn.app.noos.feature.func.news.data.remote.NewsApi
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class NewsModule {

    @Provides
    fun provideNewsApi(
        retrofit: Retrofit,
    ): NewsApi {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = NewsApi::class.java
        ).create()
    }

    @Provides
    fun provideArticlesRemoteDataSource(newsApi: NewsApi): ArticlesRemoteDataSource {
        return ArticlesRemoteDataSourceImpl(
            newsApi = newsApi
        )
    }
}