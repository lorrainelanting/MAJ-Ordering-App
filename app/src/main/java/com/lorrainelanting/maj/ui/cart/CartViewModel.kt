package com.lorrainelanting.maj.ui.cart

import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.ui.base.BaseViewModel

class CartViewModel : BaseViewModel() {
    val cartContentsLiveData by lazy { cartRepository.getList() }

    fun getProduct(id: String): Product {
        return productRepository.get(id)!!
    }

    fun updateCartContent(item: CartContent) {
        cartRepository.update(item)
    }

    fun deleteCartContent(id: String) {
        cartRepository.delete(id)
    }
}