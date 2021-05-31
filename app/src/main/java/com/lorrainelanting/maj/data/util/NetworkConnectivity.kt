package com.lorrainelanting.maj.data.util

import android.os.AsyncTask
import java.net.InetAddress

class NetworkConnectivity: AsyncTask<Boolean, Boolean, Boolean>() {
    private var isConnected: Boolean = false

    override fun doInBackground(vararg params: Boolean?): Boolean {
        return try {
            val ipAddress = InetAddress.getByName("www.google.com")
            return ipAddress.isReachable(1000)
        } catch (e: Exception) {
            false
        }
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        isConnected = result
    }
}