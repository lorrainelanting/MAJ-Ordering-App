package com.vitocuaderno.maj

import android.os.Bundle
import android.widget.ImageView
import com.vitocuaderno.maj.databinding.ActivityMainBinding

class CartActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
//        setImageProduct()
    }

//    private fun setImageProduct() {
//        return findViewById<ImageView>(R.id.imgProduct).setImageResource(R.drawable.ic_soft_drink)
//    }
}