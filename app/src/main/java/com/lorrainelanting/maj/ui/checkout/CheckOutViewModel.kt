package com.lorrainelanting.maj.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.orders.OrdersRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import com.lorrainelanting.maj.ui.cart.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository,
    private val ordersRepository: OrdersRepository,
    private val productRepository: ProductRepository
) : BaseViewModel() {
    val cartContentsLiveData by lazy { cartRepository.getList() }
    val usersLiveData by lazy { userRepository.getList() }
    val deliveryAddressesLiveData by lazy { deliveryAddressRepository.getList() }

    private val _state = MutableLiveData<CheckOutState>()
    val state: LiveData<CheckOutState> = _state

    fun deleteCartItem(id: String) {
        cartRepository.delete(id)
    }

    fun insertOrder(deliveryOption: Int, status: String, deliveryDate: Long) {
        viewModelScope.launch {
            ordersRepository.save(deliveryOption, status, deliveryDate)
        }
    }

    fun fetchProduct(productId: String) {
        viewModelScope.launch {
            val product = productRepository.get(productId)
            val message = "Product does not exist."
            product?.let {
                _state.postValue(CheckOutState.FetchedProduct(it))
            } ?: run {
                _state.postValue(CheckOutState.NothingToFetchProduct(message))
            }
        }
    }
}