package com.fwhyn.app.noos.common.di

import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.fwhyn.lib.baze.network.data.helper.NetworkMonitor
import com.fwhyn.lib.baze.network.data.helper.NetworkMonitorImpl
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
    ): NetworkMonitor {
        return NetworkMonitorImpl(
            connectivityManager,
            networkRequest
        )
    }
}