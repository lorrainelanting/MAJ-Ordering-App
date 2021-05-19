package com.lorrainelanting.maj.ui.checkout

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.di.Injection

class CheckOutViewModel: ViewModel() {
    lateinit var cartRepository: CartRepository

    lateinit var userRepository: UserRepository

    lateinit var deliveryAddressRepository: DeliveryAddressRepository

    val userLiveData by lazy {
        userRepository.getList()
    }

    val  deliveryAddressLiveData by lazy {
        deliveryAddressRepository.getList()
    }

    val cartContentsLiveData by lazy {
        cartRepository.getList()
    }

    fun injectCart(context: Context) {
        cartRepository = Injection.provideCartRepository(context)
    }

    fun injectUser(context: Context) {
        userRepository = Injection.provideUserRepository(context)
    }

    fun injectDeliveryAddress(context: Context) {
        deliveryAddressRepository = Injection.provideDeliveryAddressRepository(context)
    }
}