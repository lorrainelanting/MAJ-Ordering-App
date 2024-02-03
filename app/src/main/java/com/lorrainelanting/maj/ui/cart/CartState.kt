package com.lorrainelanting.maj.ui.cart

import com.lorrainelanting.maj.data.model.Product

sealed class CartState {
    data class FetchedProduct(val product: Product): CartState()
    data class NothingToFetchProduct(val message: String): CartState()
}
