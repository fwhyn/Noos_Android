package com.fwhyn.app.noos.common.network.helper

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// TODO move to baze
object Util {

    @JvmStatic
    fun networkIsOnline(connectivityManager: ConnectivityManager): Boolean {
        return connectivityManager.activeNetwork?.let { network ->
            connectivityManager
                .getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } == true
    }
}