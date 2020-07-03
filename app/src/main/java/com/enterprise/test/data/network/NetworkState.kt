package com.enterprise.test.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast

class NetworkState {
        var connectivityManager: ConnectivityManager? = null
        var connected = false
        val isOnline: Boolean
            get() {
                try {
                    connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val networkInfo = connectivityManager!!.activeNetworkInfo
                    connected = networkInfo != null && networkInfo.isAvailable &&
                            networkInfo.isConnected
                    return connected
                } catch (e: Exception) {
                    println("CheckConnectivity Exception: " + e.message)
                    Log.v("connectivity", e.toString())
                }
                return connected
            }

        companion object {
            var context: Context? = null
            private val instance = NetworkState()
            fun getInstance(ctx: Context): NetworkState {
                context = ctx.applicationContext
                return instance
            }
        }
    }
