package com.lorrainelanting.maj.ui.home

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.ui.base.BaseViewModel

class HomeViewModel: BaseViewModel() {
    val homeContentsLiveData by lazy { productRepository.getList() }

    fun cartContentNewInstance(product: Product, quantity: Int = 0): CartContent {
        return CartContent.newInstance(
            id = System.currentTimeMillis().toString(),
            productId = product.id,
            quantity = quantity,
        )
    }

    fun getProductLiveData(id: String): LiveData<Product> {
        return productRepository.getItem(id)
    }

    fun insertCartContent(item: CartContent) {
        cartRepository.add(item)
    }
}