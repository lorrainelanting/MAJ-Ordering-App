package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "cart_content_table")
class CartContent : BaseModel() {
    var quantity: Int = 0
    var productId: String = ""

    companion object {
        fun newInstance(id: String, productId: String, quantity: Int): CartContent {
            val cartContent = CartContent()
            cartContent.id = id
            cartContent.productId = productId
            cartContent.quantity = quantity
            return cartContent
        }
    }
}