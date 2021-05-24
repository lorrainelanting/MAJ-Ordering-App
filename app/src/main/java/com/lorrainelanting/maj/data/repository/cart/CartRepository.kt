package com.lorrainelanting.maj.data.repository.cart

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.CartContent

interface CartRepository {
    fun getList() : LiveData<List<CartContent>>

    fun getItem(id: String) : LiveData<CartContent>

    fun add(item: CartContent)

    fun update(item: CartContent)

    fun delete(id: String)
}