package com.lorrainelanting.maj.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.databinding.ActivitySplashBinding


class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    companion object {
        const val SPLASH_TIME_OUT: Long = 1500 // millisec
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}