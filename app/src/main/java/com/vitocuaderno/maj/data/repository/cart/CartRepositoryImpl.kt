package com.vitocuaderno.maj.data.repository.cart

import android.util.Log
import androidx.lifecycle.LiveData
import com.vitocuaderno.maj.data.local.CartContentDao
import com.vitocuaderno.maj.data.model.CartContent

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

    override fun getList(): LiveData<List<CartContent>> = dao.getList()

    override fun getItem(id: Int): LiveData<CartContent> = dao.getItem(id)

    override fun add(item: CartContent) {
        val existingItem = dao.getItemByProduct(item.productId)

        if(existingItem != null) {
            existingItem.quantity += item.quantity
            dao.update(existingItem)
        } else {
            dao.insert(item)
        }
    }

    override fun delete(id: Int) = dao.delete(id)
}