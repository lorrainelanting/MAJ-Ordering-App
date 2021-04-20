package com.vitocuaderno.maj.data.model

class HomeContent(var id: Int) : BaseModel(id) {
    var productImgUrl: String = "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg"
    var productDescription: String = ""
    var productUnitCost: Double = 0.00
    var productPackQty: String = ""
    var isAddedToCart: Boolean = false // TODO: check if product added to cart
}