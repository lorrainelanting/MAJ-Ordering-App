package com.vitocuaderno.maj.data.repository

import androidx.lifecycle.LiveData
import com.vitocuaderno.maj.data.model.Product

interface ProductRepository {
    fun getList() : LiveData<List<Product>>

    fun getItem(id: Int) : LiveData<Product>
}