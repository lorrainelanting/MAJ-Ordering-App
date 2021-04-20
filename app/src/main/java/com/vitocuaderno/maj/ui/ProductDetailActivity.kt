package com.vitocuaderno.maj.ui.home

import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.HomeContent
import com.vitocuaderno.maj.databinding.ActivityHomeBinding
import com.vitocuaderno.maj.ui.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.let {
            Toast.makeText(this, it.getInt("id").toString(), Toast.LENGTH_SHORT).show()
        }
        //  Load image from url string
        val picasso = Picasso.get()
        for (i in 0..10) {
            val content : HomeContent = HomeContent(i)
            picasso.load(content.productImgUrl).error(R.drawable.ic_homepage).into(
                binding.imgProduct,
                object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                }
            )
        }
    }
}