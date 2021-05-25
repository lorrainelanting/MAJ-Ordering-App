package com.lorrainelanting.maj.ui.checkout

import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.order.OrderRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.data.util.Constants

class CheckOutViewModel(
    val cartRepository: CartRepository,
    val productRepository: ProductRepository,
    val userRepository: UserRepository,
    val deliveryAddressRepository: DeliveryAddressRepository,
    val orderRepository: OrderRepository
) : ViewModel() {

    val userLiveData by lazy { userRepository.getList() }

    val deliveryAddressLiveData by lazy { deliveryAddressRepository.getList() }

    val cartContentsLiveData by lazy { cartRepository.getList() }

    fun ordersContentNewInstance(id: String, content: CheckOutOrderSummaryContentAdapter.Content): Order {
        return Order.newInstance(
            id = id,
            deliveryOption = Constants.DELIVER,
            status = Constants.PREPARING,
            productId = content.cartContent.productId,
            productName = content.product.name,
            productImgUrl = content.product.imgUrl,
            quantity = content.cartContent.quantity,
            productPrice = content.product.price,
            productPackQty = content.product.packQty
        )
    }
}