package com.vitocuaderno.maj.data.repository.cart

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

    override fun addToCart(item: CartContent) = dao.insert(item)

    override fun delete(id: Int) = dao.delete(id)
}