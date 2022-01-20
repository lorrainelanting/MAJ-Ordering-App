package com.lorrainelanting.maj.data.model

import androidx.room.Entity

// Parent of Order entity
@Entity(tableName = "order_group_table")
class OrderGroup : BaseModel() {
    var size: Int? = null
    var deliveryOption: Int = -1
    var status: String = ""

    // Customer info
    var customerName: String = ""
    var customerContactNum: String = ""
    var customerStoreName: String? = null

    // Delivery details
    var deliveryAddress: String = ""
    var deliveryDate: Long = System.currentTimeMillis() / 1000

    companion object {
        fun newInstance(
            deliveryOption: Int,
            status: String,
            customerName: String,
            customerContactNum: String,
            customerStoreName: String = "",
            deliveryAddress: String,
            deliveryDate: Long
        ): OrderGroup {
            val orderGroup = OrderGroup()
            orderGroup.deliveryOption = deliveryOption
            orderGroup.status = status
            orderGroup.customerName = customerName
            orderGroup.customerContactNum = customerContactNum
            orderGroup.customerStoreName = customerStoreName
            orderGroup.deliveryAddress = deliveryAddress
            orderGroup.deliveryDate = deliveryDate

            return orderGroup
        }
    }
}