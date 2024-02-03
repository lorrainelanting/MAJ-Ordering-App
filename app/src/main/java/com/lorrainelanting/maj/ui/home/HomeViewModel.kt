package com.lorrainelanting.maj.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {
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
        viewModelScope.launch {
            cartRepository.add(item)
        }
    }
}