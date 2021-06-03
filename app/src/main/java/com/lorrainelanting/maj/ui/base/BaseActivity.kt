package com.lorrainelanting.maj.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.util.NetworkConnectivity
import java.net.InetAddress

abstract class BaseActivity<B : ViewDataBinding>: AppCompatActivity(), NetworkConnectivity {
    var isNetworkAvailable: Boolean = false
        get() = field
        private set(value) {
            field = value
        }

    lateinit var binding: B

    private lateinit var connectivityReceiver : ConnectivityReceiver

    private class ConnectivityReceiver(private val activity: com.lorrainelanting.maj.data.util.NetworkConnectivity) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            activity.checkNetworkAvailability()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            getLayoutId()
        )
        binding.lifecycleOwner = this

        connectivityReceiver = ConnectivityReceiver(this)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        checkNetworkAvailability()
    }

    override fun checkNetworkAvailability() {
        val networkConnectivity = NetworkConnectivity()

        val isConnected = networkConnectivity.execute()
        val banner = findViewById<CardView>(R.id.layoutNetworkBanner) ?: return

        if (isConnected.get()) {
            // hide banner
            banner.visibility = View.GONE
        } else {
            // show banner
            banner.visibility = View.VISIBLE
            setNetworkBannerTooltip()
        }
        isNetworkAvailable = isConnected.get()
    }

    private class NetworkConnectivity : AsyncTask<Boolean, Boolean, Boolean>() {
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

    private fun setNetworkBannerTooltip() {
        val tooltipText = resources.getText(R.string.txt_set_network_unavailable_tooltip)
        val imgBtnInfo = findViewById<ImageView>(R.id.imgBtnInfo)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            TooltipCompat.setTooltipText(imgBtnInfo, tooltipText)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imgBtnInfo.tooltipText = tooltipText
        }
    }
}