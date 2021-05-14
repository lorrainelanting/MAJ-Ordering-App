package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "product_table")
class Product() : BaseModel() {
    var imgUrl: String = ""
    var name: String = ""
    var description: String = ""
    var unitCost: Double = 0.00
    var packQty: String = "${12}pcs. per pack"
    var isAddedToCart: Boolean = false // TODO: check if product added to cart

    companion object {
        fun newInstance(productName: String, productImgUrl: String, productDescription: String, productUnitCost: Double) : Product {
            val product = Product()
            product.name = productName
            product.imgUrl = productImgUrl
            product.description = productDescription
            product.unitCost = productUnitCost

            return product
        }
    }
}