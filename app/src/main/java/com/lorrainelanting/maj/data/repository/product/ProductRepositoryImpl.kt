package com.lorrainelanting.maj.data.repository.product

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.ProductDao
import com.lorrainelanting.maj.data.model.Product

class ProductRepositoryImpl private constructor(private val dao: ProductDao):
    ProductRepository {
    companion object {
        private var instance: ProductRepository? = null

        fun getInstance(dao: ProductDao) : ProductRepository {
            if (instance == null) {
                instance =
                    ProductRepositoryImpl(
                        dao
                    )
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<Product>> = dao.getList()

    override fun getItem(id: Int): LiveData<Product> = dao.getItem(id)
}