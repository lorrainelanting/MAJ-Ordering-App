package com.lorrainelanting.maj.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "order_table"
)
class Order : BaseModel() {
    var orderGroupId: String = "0" // foreignKey of parent entity
    var quantity: Int = 0

    //  product details
    var productId: String = ""
    var productName: String = ""
    var productImgUrl: String = ""
    var productDescription: String = ""
    var productPrice: Double = 0.00
    var productPackQty: String = "${12}pcs. per pack"

    companion object {
        fun newInstance(
            productId: String,
            productName: String,
            productImgUrl: String,
            quantity: Int,
            productPrice: Double,
            productPackQty: String
        ): Order {
            val order = Order()
            order.quantity = quantity
//            Product Snapshot
            order.productId = productId
            order.productName = productName
            order.productImgUrl = productImgUrl
            order.productPrice = productPrice
            order.productPackQty = productPackQty
            return order
        }
    }
}