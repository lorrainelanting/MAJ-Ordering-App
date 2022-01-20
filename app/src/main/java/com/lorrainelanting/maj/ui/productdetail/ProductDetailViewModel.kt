package com.lorrainelanting.maj.ui.productdetail

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.ui.base.BaseViewModel

class ProductDetailViewModel: BaseViewModel() {
    fun getProductLiveData(id: String): LiveData<Product> {
        return productRepository.getItem(id)
    }

    fun cartContentNewInstance(product: Product, quantity: Int = 0) : CartContent {
        return CartContent.newInstance(
            id = System.currentTimeMillis().toString(),
            productId = product.id,
            quantity = quantity,
        )
    }

    fun insertProductToCart(item: CartContent) {
        cartRepository.add(item)
    }
}