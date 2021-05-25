package com.lorrainelanting.maj.ui.cart

import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository

class CartViewModel(
    val cartRepository: CartRepository,
    val productRepository: ProductRepository,
    val userRepository: UserRepository,
    val deliveryAddressRepository: DeliveryAddressRepository
) :
    ViewModel() {
    val cartContentsLiveData by lazy {
        cartRepository.getList()
    }

    val userLiveData by lazy {
        userRepository.getList()
    }

    val deliveryAddressLiveData by lazy {
        deliveryAddressRepository.getList()
    }
}