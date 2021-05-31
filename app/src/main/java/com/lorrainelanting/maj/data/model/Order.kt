package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "order_table")
class Order : BaseModel() {
    var quantity: Int = 0
    var deliveryOption: Int = -1
    var status: String = ""
//    product details
    var productId: String = ""
    var productName: String = ""
    var productImgUrl: String = ""
    var productDescription: String = ""
    var productPrice: Double = 0.00
    var productPackQty: String = "${12}pcs. per pack"
//    Customer info
    var customerName: String = ""
    var customerContactNum: String = ""
    var deliveryAddress: String = ""
    var deliveryDate: Long = System.currentTimeMillis() / 1000


    companion object {
        fun newInstance(
            id:String,
            deliveryOption: Int,
            status: String,
            productId: String,
            productName: String,
            productImgUrl: String,
            quantity: Int,
            productPrice: Double,
            productPackQty: String,
            customerName: String,
            customerContactNum: String,
            deliveryAddress: String,
            deliveryDate: Long
        ): Order {
            val order = Order()
            order.id = id
            order.deliveryOption = deliveryOption
            order.status = status
            order.quantity = quantity
//            Product Snapshot
            order.productId = productId
            order.productName = productName
            order.productImgUrl = productImgUrl
            order.productPrice = productPrice
            order.productPackQty = productPackQty
//            Customer Info
            order.customerName = customerName
            order.customerContactNum = customerContactNum
            order.deliveryAddress = deliveryAddress
            order.deliveryDate = deliveryDate
            return order
        }
    }
}