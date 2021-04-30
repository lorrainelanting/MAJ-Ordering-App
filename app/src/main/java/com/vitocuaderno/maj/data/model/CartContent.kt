package com.vitocuaderno.maj.data.model

import androidx.room.Entity

@Entity(tableName = "cart_content_table")
class CartContent(): BaseModel() {
    var quantity: Int = 0
    var productId: Int = -1
    var productName: String = ""
    var productImgUrl: String = ""
    var productDescription: String = ""
    var productUnitCost: Double = 0.00
    var productPackQty: String = "${12}pcs. per pack"

    companion object {
        fun newInstance(productId: Int, productName: String, productImgUrl: String, quantity: Int, productUnitCost: Double) : CartContent {
            val cartContent = CartContent()
            cartContent.productId = productId
            cartContent.productName = productName
            cartContent.productImgUrl = productImgUrl
            cartContent.productUnitCost = productUnitCost
            cartContent.quantity = quantity

            return cartContent
        }
    }
}