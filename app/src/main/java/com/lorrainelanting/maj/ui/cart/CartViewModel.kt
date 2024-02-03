package com.lorrainelanting.maj.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : BaseViewModel() {
    val cartContentsLiveData by lazy { cartRepository.getList() }

    private val _state = MutableLiveData<CartState>()
    val state: LiveData<CartState> = _state

    fun fetchProduct(id: String) {
        viewModelScope.launch {
            val product = productRepository.get(id)
            val message = "Product does not exist."
            product?.let {
                _state.postValue(CartState.FetchedProduct(it))
            } ?: run {
                _state.postValue(CartState.NothingToFetchProduct(message))
            }
        }
    }

    fun updateCartContent(item: CartContent) {
        cartRepository.update(item)
    }

    fun deleteCartContent(id: String) {
        cartRepository.delete(id)
    }
}