package com.vitocuaderno.maj.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.data.model.Product
import com.vitocuaderno.maj.data.repository.cart.CartRepository
import com.vitocuaderno.maj.data.repository.product.ProductRepository
import com.vitocuaderno.maj.di.Injection

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
            productId = product.id,
            productName = product.name,
            productImgUrl = product.imgUrl,
            productUnitCost = product.unitCost,
            quantity = quantity,
            productPackQty = product.packQty
        )
    }
}