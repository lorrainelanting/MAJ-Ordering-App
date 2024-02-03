package com.lorrainelanting.maj.data.repository.orders

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.OrderGroup

interface OrdersRepository {
    fun getOrderList() : LiveData<List<Order>>

    fun getOrderGroupList(): LiveData<List<OrderGroup>>

    fun getItem(id: String) : LiveData<Order>

    fun getItemByGroup(id: String) : OrderGroup?

    suspend fun save(deliveryOption: Int, status: String, deliveryDate: Long)

    fun update(orderGroup: OrderGroup)

    fun delete(id: String)

    fun getOrdersByGroup(orderGroupId: String): LiveData<List<Order>>

    fun getOrderGroup(orderGroupId: String): OrderGroup

    fun getOrderSizeByGroupId(orderGroupId: String): Int
}