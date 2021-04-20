package com.vitocuaderno.maj.data.model

data class CartContent(var id: Int): BaseModel(id) {
    var productName: String = ""
    var productImgUrl: String = "" // TODO: set to String url
    var productUnitCost: Double = 0.00
    var quantity: Int = 0
}