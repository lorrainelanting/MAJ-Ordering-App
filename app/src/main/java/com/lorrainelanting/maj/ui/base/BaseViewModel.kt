package com.lorrainelanting.maj.ui.base

import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.orders.OrdersRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.di.Injection

abstract class BaseViewModel: ViewModel() {

    @CallSuper
    open fun start() {
        // TODO
    }

//    lateinit var cartRepository: CartRepository
//    lateinit var productRepository: ProductRepository
//    lateinit var ordersRepository: OrdersRepository
//    lateinit var userRepository: UserRepository
//    lateinit var deliveryAddressRepository: DeliveryAddressRepository
//
//    fun initializedRepositories (context: Context) {
//        cartRepository = Injection.provideCartRepository(context)
//        productRepository = Injection.provideProductRepository(context)
//        ordersRepository = Injection.provideOrdersRepository(context)
//        userRepository = Injection.provideUserRepository(context)
//        deliveryAddressRepository = Injection.provideDeliveryAddressRepository(context)
//    }
}