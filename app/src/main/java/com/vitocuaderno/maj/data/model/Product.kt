package com.vitocuaderno.maj.data.model

import androidx.room.Entity

@Entity(tableName = "home_content_table")
class Product(id: Int) : BaseModel(id) {
    var productImgUrl: String = "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg"
    var productDescription: String = ""
    var productUnitCost: Double = 0.00
    var productPackQty: String = "${12}pcs. per pack"
    var isAddedToCart: Boolean = false // TODO: check if product added to cart

    companion object {
        fun newInstance(id: Int, productImgUrl: String, productDescription: String, productUnitCost: Double) : Product {
            val homeContent = Product(id)
            homeContent.productImgUrl = productImgUrl
            homeContent.productDescription = productDescription
            homeContent.productUnitCost = productUnitCost

            return homeContent
        }
    }
}