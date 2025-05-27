package com.fwhyn.app.noos.feature.func.news.di

import com.fwhyn.app.noos.common.network.helper.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
class RetrofitModule {

    @Qualifier
    annotation class NewsBaseApi

    @Provides
    @NewsBaseApi
    fun provideRetrofit(): Retrofit = RetrofitProvider.get("https://newsapi.org/v2/")
}