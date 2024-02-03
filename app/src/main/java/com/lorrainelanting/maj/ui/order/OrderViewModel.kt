package com.lorrainelanting.maj.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.OrderGroup
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.orders.OrdersRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {
    val orderGroupLiveData by lazy { ordersRepository.getOrderGroupList() }

    fun getOrderSizeByGroupId(orderGroupId: String): Int {
        return ordersRepository.getOrderSizeByGroupId(orderGroupId)
    }

    fun getOrderGroup(id: String): OrderGroup {
        return ordersRepository.getOrderGroup(id)
    }

    fun getOrdersByGroupLiveData(orderGroupId: String): LiveData<List<Order>> {
        return ordersRepository.getOrdersByGroup(orderGroupId)
    }

    fun updateOrderGroup(orderGroup: OrderGroup) {
        ordersRepository.update(orderGroup)
    }

    fun cartContentNewInstance(product: Product, quantity: Int = 0): CartContent {
        return CartContent.newInstance(
            id = System.currentTimeMillis().toString(),
            productId = product.id,
            quantity = quantity,
        )
    }

    fun reorderItem(cartContent: CartContent) {
        viewModelScope.launch {
            cartRepository.add(cartContent)
        }
    }
}