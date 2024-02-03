package com.lorrainelanting.maj.data.repository.product

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getList() : LiveData<List<Product>>

    fun getItem(id: String) : LiveData<Product>

    suspend fun get(id: String): Product?

    fun insert(product: Product)

    fun insertAll(products: List<Product>)

    fun deleteAll()
}