package com.fwhyn.app.noos.common.di

import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.fwhyn.app.noos.common.network.helper.NetworkMonitorImpl
import com.fwhyn.lib.baze.network.data.helper.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkMonitorModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        connectivityManager: ConnectivityManager,
        @NetworkModule.Internet networkRequest: NetworkRequest,
    ): NetworkMonitor = NetworkMonitorImpl(
        connectivityManager,
        networkRequest
    )
}