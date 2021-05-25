package com.lorrainelanting.maj.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.di.Injection

class HomeViewModel: ViewModel() {
    lateinit var repository: ProductRepository
    lateinit var cartRepository: CartRepository

    val homeContentsLiveData by lazy {
        repository.getList()
    }

    fun injectProduct(context: Context) {
        repository = Injection.provideProductRepository(context)
    }

    fun injectCart(context: Context) {
        cartRepository = Injection.provideCartRepository(context)
    }

    fun cartContentNewInstance(product: Product, quantity: Int = 0) : CartContent {
       return CartContent.newInstance(
           id = System.currentTimeMillis().toString(),
            productId = product.id,
            quantity = quantity,
        )
    }
}