package com.vitocuaderno.maj.data.repository.cart

import androidx.lifecycle.LiveData
import com.vitocuaderno.maj.data.model.CartContent

interface CartRepository {
    fun getList() : LiveData<List<CartContent>>

    fun getItem(id: Int) : LiveData<CartContent>

    fun add(item: CartContent)

    fun delete(id: Int)
}