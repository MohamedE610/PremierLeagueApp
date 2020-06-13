package com.example.premierleagueapp.core.presentation.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.premierleagueapp.core.presentation.extensions.isInternetAvailable
import com.example.premierleagueapp.core.presentation.modelwraper.SingleLiveEvent
import javax.inject.Inject

class InternetConnectivityListener @Inject constructor(private val applicationContext: Context) {

    private val internetBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i("isInternetAvailable", "called")
            if (!isInitialStickyBroadcast) {
                val isInternetAvailable = context?.isInternetAvailable()
                internetConnectivityLiveData.value = isInternetAvailable
                Log.i("isInternetAvailable", isInternetAvailable.toString())
            }
        }
    }

    var internetConnectivityLiveData = SingleLiveEvent<Boolean>()

    fun registerInternetReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
        applicationContext.registerReceiver(internetBroadcastReceiver, intentFilter)
    }

    fun unregisterInternetReceiver() {
        try {
            applicationContext.unregisterReceiver(internetBroadcastReceiver)
        } catch (ex: Exception) {
        }
    }
}