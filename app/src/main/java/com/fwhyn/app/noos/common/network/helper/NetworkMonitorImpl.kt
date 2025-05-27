package com.fwhyn.app.noos.common.network.helper

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.fwhyn.lib.baze.network.data.helper.NetworkMonitor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

// TODO move to baze
class NetworkMonitorImpl(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest,
) : NetworkMonitor {

    override val isOnline: Flow<Boolean> = callbackFlow {
        // Initial status check
        val currentStatus = Util.networkIsOnline(connectivityManager)

        trySend(currentStatus) // Send initial value

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onUnavailable() {
                trySend(false)
            }
        }

        register(callback)

        awaitClose {
            unregister(callback)
        }
    }.distinctUntilChanged()


    fun register(callback: ConnectivityManager.NetworkCallback) {
        connectivityManager.registerNetworkCallback(networkRequest, callback)
    }

    fun unregister(callback: ConnectivityManager.NetworkCallback) {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}