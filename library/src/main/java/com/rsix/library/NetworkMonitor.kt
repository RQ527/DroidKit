package com.rsix.library

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkMonitor private constructor() {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { NetworkMonitor() }
        const val WIFI = 0
        const val MOBILE = 1
        const val ETHERNET = 3
        const val VPN = 4
        const val NO_CONNECTIVITY = 5
        const val OTHER = 6
    }

    private var _network = NO_CONNECTIVITY
        set(value) {
            field = value
            listeners.forEach {
                it.onNetChanged(value)
            }
        }
    val network
        get() = _network

    fun init(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(network) ?: return
                _network = networkCapabilities.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) WIFI
                    else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) MOBILE
                    else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)) VPN
                    else if (hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) ETHERNET
                    else OTHER
                }
            }

            override fun onLost(network: Network) {
                _network = NO_CONNECTIVITY
            }
        })
    }

    private val listeners = mutableListOf<NetWorkListener>()
    fun addNetWorListener(listener: NetWorkListener) {
        listeners.add(listener)
    }

    fun removeNetWorkListener(listener: NetWorkListener) {
        listeners.remove(listener)
    }

    fun interface NetWorkListener {
        fun onNetChanged(network: Int)
    }
}