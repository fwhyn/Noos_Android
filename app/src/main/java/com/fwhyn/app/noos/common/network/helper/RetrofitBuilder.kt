package com.fwhyn.app.noos.common.network.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO move to baze
class RetrofitBuilder(private val baseUrl: String) {
    private val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    fun addBearerAuth(onGetKey: (() -> String)): RetrofitBuilder {
        val apiRequestInterceptor = ApiRequestInterceptor(onGetKey)
        okHttpClientBuilder.addNetworkInterceptor(apiRequestInterceptor)

        return this
    }

    fun enableLog(): RetrofitBuilder {
        val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(logger)

        return this
    }

    fun build(): Retrofit {
        val retrofitBuilder = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())

        return retrofitBuilder.build()
    }
}