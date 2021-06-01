package com.lorrainelanting.maj.data.repository.order

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.OrderDao
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.util.Constants

class OrderRepositoryImpl private constructor(private val dao: OrderDao): OrderRepository {

    companion object {
        private var instance: OrderRepository? = null

        fun getInstance(dao: OrderDao) : OrderRepository {
            if (instance == null) {
                instance =
                    OrderRepositoryImpl(dao)
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<Order>> = dao.getList()

    override fun getItem(id: String): LiveData<Order> = dao.getItem(id)

    override fun save(order: Order) {
        val existingItem = dao.getItemByProduct(order.productId)

        if(existingItem != null && existingItem.status == Constants.STATUS_PLACED_ORDER) {
            val newId = dao.getOrders().size + 1
            for (item in dao.getOrders()) {
                order.id = newId.toString()
            }

            existingItem.quantity += order.quantity
            dao.update(existingItem)
        } else {
            for ((i, value) in dao.getOrders().withIndex()) {
                order.id = i.toString()
            }
            dao.insert(order)
        }
    }

    override fun update(order: Order) = dao.update(order)

    override fun delete(id: String) = dao.delete(id)
}