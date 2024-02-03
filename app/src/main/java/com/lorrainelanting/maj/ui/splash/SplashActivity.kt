package com.lorrainelanting.maj.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.lorrainelanting.maj.BuildConfig
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.databinding.ActivitySplashBinding
import com.lorrainelanting.maj.ui.base.BaseActivity
import com.lorrainelanting.maj.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    companion object {
        const val SPLASH_TIME_OUT: Long = 1500 // millisec
    }

    override val viewModel: SplashViewModel by viewModels()

    override fun getLayoutId() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set version name
        val versionName = BuildConfig.VERSION_NAME
        binding.txtAppVersion.text = getString(R.string.txt_splash_version, versionName)

        Handler(Looper.getMainLooper()).postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}