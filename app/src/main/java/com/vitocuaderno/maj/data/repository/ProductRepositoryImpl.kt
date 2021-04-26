package com.vitocuaderno.maj.data.repository

import androidx.lifecycle.LiveData
import com.vitocuaderno.maj.data.local.ProductDao
import com.vitocuaderno.maj.data.model.Product

class ProductRepositoryImpl private constructor(private val dao: ProductDao): ProductRepository {
    companion object {
        private var instance: ProductRepository? = null

        fun getInstance(dao: ProductDao) : ProductRepository {
            if (instance == null) {
                instance = ProductRepositoryImpl(dao)
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<Product>> = dao.getList()

    override fun getItem(id: Int): LiveData<Product> = dao.getItem(id)
}