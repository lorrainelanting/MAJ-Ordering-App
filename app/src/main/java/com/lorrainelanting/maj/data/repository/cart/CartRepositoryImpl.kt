package com.lorrainelanting.maj.data.repository.cart

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.CartContentDao
import com.lorrainelanting.maj.data.model.CartContent

class CartRepositoryImpl private constructor(private val dao: CartContentDao): CartRepository {
    companion object {
        private var instance: CartRepository? = null

        fun getInstance(dao: CartContentDao) : CartRepository {
            if (instance == null) {
                instance =
                    CartRepositoryImpl(dao)
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<CartContent>> = dao.getLiveDataList()

    override fun getItem(id: String): LiveData<CartContent> = dao.getItem(id)

    override suspend fun add(item: CartContent) {
        val existingItem = dao.getItemByProduct(item.productId)

        if(existingItem != null) {
            existingItem.quantity += item.quantity
            dao.update(existingItem)
        } else {
            dao.insert(item)
        }
    }

    override fun update(item: CartContent) = dao.update(item)

    override fun delete(id: String) = dao.delete(id)
}