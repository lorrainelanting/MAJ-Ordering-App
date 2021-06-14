package com.lorrainelanting.maj.ui.order

import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.order.OrderRepository

class OrderViewModel(val orderRepository: OrderRepository, val cartRepository: CartRepository) : ViewModel() {
    val ordersContentLiveData by lazy { orderRepository.getList() }

    val cartContentsLiveData by lazy { cartRepository.getList() }

    fun cartContentNewInstance(productId: String, quantity: Int = 0) : CartContent {
        return CartContent.newInstance(
            id = System.currentTimeMillis().toString(),
            productId = productId,
            quantity = quantity,
        )
    }
}