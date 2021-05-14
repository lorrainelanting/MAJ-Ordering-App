package com.lorrainelanting.maj.ui.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.di.Injection

class CartViewModel: ViewModel() {
    lateinit var repository: CartRepository

    val cartContentsLiveData by lazy {
        repository.getList()
    }

    fun injectCart(context: Context) {
        repository = Injection.provideCartRepository(context)
    }
}