package com.lorrainelanting.maj.ui.productdetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.di.Injection

class ProductDetailViewModel: ViewModel() {
    lateinit var repository: ProductRepository
    lateinit var cartRepository: CartRepository

    lateinit var productLiveData: LiveData<Product>

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