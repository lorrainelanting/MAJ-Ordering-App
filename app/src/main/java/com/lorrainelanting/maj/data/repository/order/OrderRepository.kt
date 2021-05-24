package com.lorrainelanting.maj.data.repository.order

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.Order

interface OrderRepository {
    fun getList() : LiveData<List<Order>>

    fun getItem(id: String) : LiveData<Order>

    fun save(order: Order)

    fun update(order: Order)

    fun delete(id: String)
}