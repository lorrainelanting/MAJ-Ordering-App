package com.lorrainelanting.maj.ui.main

import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.orders.OrdersRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository
) : BaseViewModel() {
    val cartContentsLiveData by lazy { cartRepository.getList() }
    val ordersContentLiveData by lazy { ordersRepository.getOrderGroupList() }
}