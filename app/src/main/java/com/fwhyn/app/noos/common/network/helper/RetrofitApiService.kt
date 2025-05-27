package com.fwhyn.app.noos.common.network.helper

import retrofit2.Retrofit

// TODO move to baze
class RetrofitApiService<T>(
    private val retrofit: Retrofit,
    private val cls: Class<T>,
) {

    fun create(): T = retrofit.create(cls)
}