package com.vitocuaderno.maj.ui

import android.os.Bundle
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    companion object {
        const val PROFILE: String = "profile"
    }

    override fun getLayoutId(): Int = R.layout.activity_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}