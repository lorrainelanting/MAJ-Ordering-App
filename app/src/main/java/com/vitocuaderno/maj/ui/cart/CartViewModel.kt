package com.vitocuaderno.maj.ui.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vitocuaderno.maj.data.repository.cart.CartRepository
import com.vitocuaderno.maj.di.Injection

class CartViewModel: ViewModel() {
    lateinit var repository: CartRepository

    val cartContentsLiveData by lazy {
        repository.getList()
    }

    fun injectCart(context: Context) {
        repository = Injection.provideCartRepository(context)
    }
}