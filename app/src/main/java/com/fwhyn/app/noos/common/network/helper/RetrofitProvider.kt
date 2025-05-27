package com.fwhyn.app.noos.common.network.helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    @JvmStatic
    fun get(baseUrl: String): Retrofit {

        val retrofitBuilder = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        return retrofitBuilder.build()
    }
}