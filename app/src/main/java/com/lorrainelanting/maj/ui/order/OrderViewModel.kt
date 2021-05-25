package com.lorrainelanting.maj.ui.order

import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.order.OrderRepository

class OrderViewModel(val repository: OrderRepository) : ViewModel() {
    val ordersContentLiveData by lazy { repository.getList() }
}