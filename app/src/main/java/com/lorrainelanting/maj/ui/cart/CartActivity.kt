package com.lorrainelanting.maj.ui.cart

import android.os.Bundle
import androidx.activity.viewModels
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.databinding.ActivityCartBinding
import com.lorrainelanting.maj.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {
    var adapter: CartContentsAdapter? = null
    var cartContents = mutableListOf<CartContent>()
    companion object {
        const val CART: String = "cart"
    }

    override val viewModel: CartViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        adapter = CartContentsAdapter(cartContents)
//        binding.rvCartContents.adapter = adapter
    }
}