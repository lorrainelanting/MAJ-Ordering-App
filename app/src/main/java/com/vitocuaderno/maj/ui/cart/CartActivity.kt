package com.vitocuaderno.maj.ui.cart

import android.os.Bundle
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.databinding.ActivityCartBinding
import com.vitocuaderno.maj.ui.BaseActivity


class CartActivity : BaseActivity<ActivityCartBinding>() {

    var adapter: CartContentsAdapter? = null
    var cartContents = mutableListOf<CartContent>()
    companion object {
        const val CART: String = "cart"
    }

    override fun getLayoutId(): Int = R.layout.activity_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CartContentsAdapter(cartContents)
        binding.rvCartContents.adapter = adapter
        fetchCartItems()
    }

    private fun fetchCartItems() {

//        TODO: Show loading
        for (i in 0..10) {
            val cartContent = CartContent(i)
            cartContent.productImgUrl = ""
            cartContent.productName = "Coke mismo"
            cartContent.productUnitCost = 135.00
            cartContent.quantity = 3
            cartContents.add(cartContent)
        }
//        TODO: Hide loading
        adapter?.notifyDataSetChanged()
    }
}