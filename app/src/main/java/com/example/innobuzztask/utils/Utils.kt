package com.example.innobuzztask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest


object Utils {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"
    const val KEY_POST = "KEY_POST"

    fun isConnected(context: Context, onAvailable: (() -> Unit)?, onLost: (() -> Unit)?): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    onAvailable?.let {
                        it()
                    }
                }

                override fun onLost(network: Network) {
                    onLost?.let {
                        it()
                    }
                }
            }
        )
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}