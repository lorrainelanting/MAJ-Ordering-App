package com.lorrainelanting.maj.data.repository.product

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.Product

interface ProductRepository {
    fun getList() : LiveData<List<Product>>

    fun getItem(id: Int) : LiveData<Product>
}