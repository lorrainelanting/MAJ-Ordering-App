package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "product_table")
class Product() : BaseModel() {
    var imgUrl: String = ""
    var name: String = ""
    var description: String = ""
    var price: Double = 0.00
    var packQty: String = "${12}pcs. per pack"

    companion object {
        fun newInstance(id:String, name: String, imgUrl: String, description: String, price: Double) : Product {
            val product = Product()
            product.id = id
            product.name = name
            product.imgUrl = imgUrl
            product.description = description
            product.price = price

            return product
        }
    }
}