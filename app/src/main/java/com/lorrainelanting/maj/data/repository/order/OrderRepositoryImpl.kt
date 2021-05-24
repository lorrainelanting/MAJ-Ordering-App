package com.lorrainelanting.maj.data.repository.order

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.OrderDao
import com.lorrainelanting.maj.data.model.Order

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

    override fun save(order: Order) = dao.insert(order)

    override fun update(order: Order) = dao.update(order)

    override fun delete(id: String) = dao.delete(id)
}