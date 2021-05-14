package com.lorrainelanting.maj.ui.checkout

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.di.Injection

class CheckOutViewModel: ViewModel() {
    lateinit var cartRepository: CartRepository

    val cartContentsLiveData by lazy {
        cartRepository.getList()
    }

    fun injectCart(context: Context) {
        cartRepository = Injection.provideCartRepository(context)
    }

}