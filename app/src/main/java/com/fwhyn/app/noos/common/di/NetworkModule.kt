package com.fwhyn.app.noos.common.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.fwhyn.lib.baze.network.data.helper.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Qualifier
    annotation class Internet

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context,
    ): ConnectivityManager {
        return Util.getConnectivityManager(context)
    }

    @Provides
    @Singleton
    @Internet
    fun provideNetworkRequestInternet(): NetworkRequest {
        return Util.getNetworkRequestInternet()
    }
}