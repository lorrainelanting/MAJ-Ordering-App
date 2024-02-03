package com.lorrainelanting.maj.ui.checkout

import com.lorrainelanting.maj.data.model.Product

sealed class CheckOutState {
    data class FetchedProduct(val product: Product): CheckOutState()
    data class NothingToFetchProduct(val message: String): CheckOutState()
}
