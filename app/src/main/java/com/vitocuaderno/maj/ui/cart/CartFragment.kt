package com.vitocuaderno.maj.ui.cart

import android.os.Bundle
import android.view.View
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.databinding.FragmentCartBinding
import com.vitocuaderno.maj.ui.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_cart
    var adapter: CartContentsAdapter? = null
    var cartContents = mutableListOf<CartContent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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