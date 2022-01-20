package com.lorrainelanting.maj.ui.checkout

import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.ui.base.BaseViewModel

class CheckOutViewModel : BaseViewModel() {
    val cartContentsLiveData by lazy { cartRepository.getList() }
    val usersLiveData by lazy { userRepository.getList() }
    val deliveryAddressesLiveData by lazy { deliveryAddressRepository.getList() }

    fun deleteCartItem(id: String) {
        cartRepository.delete(id)
    }

    fun insertOrder(deliveryOption: Int, status: String, deliveryDate: Long) {
        ordersRepository.save(deliveryOption, status, deliveryDate)
    }

    fun getProduct(productId: String): Product? {
        return productRepository.get(productId)
    }
}