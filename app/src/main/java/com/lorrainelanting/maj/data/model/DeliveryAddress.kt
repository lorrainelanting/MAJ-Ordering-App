package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "delivery_address_table")
class DeliveryAddress: BaseModel() {
    var city: String = ""
    var barangay: String = ""
    var streetName: String = ""

    companion object {
        fun newInstance(city: String, barangay: String, streetName: String): DeliveryAddress {
            val deliveryAddress = DeliveryAddress()
            deliveryAddress.city = city
            deliveryAddress.barangay = barangay
            deliveryAddress.streetName = streetName

            return deliveryAddress
        }
    }
}