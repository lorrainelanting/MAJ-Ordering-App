package com.vitocuaderno.maj

import android.os.Bundle
import com.vitocuaderno.maj.databinding.ActivityMainBinding

class ProfileActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_cart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }
}