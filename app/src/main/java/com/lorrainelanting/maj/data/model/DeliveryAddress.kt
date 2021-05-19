package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "delivery_address_table")
class DeliveryAddress: BaseModel() {
    var city: String = ""
    var barangay: String = ""
    var streetName: String = ""
}