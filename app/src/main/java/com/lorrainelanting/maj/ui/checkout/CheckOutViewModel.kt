package com.lorrainelanting.maj.ui.checkout

import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.order.OrderRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository

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

    fun ordersContentNewInstance(deliveryOption: Int, status: String, content: CheckOutOrderSummaryContentAdapter.Content, customer: User, deliveryAddress: String, deliveryDate: Long): Order {
        return Order.newInstance(
            deliveryOption = deliveryOption,
            status = status,
            productId = content.cartContent.productId,
            productName = content.product.name,
            productImgUrl = content.product.imgUrl,
            quantity = content.cartContent.quantity,
            productPrice = content.product.price,
            productPackQty = content.product.packQty,
            customerName = customer.fullName,
            customerContactNum = customer.contactNum,
            deliveryAddress = deliveryAddress,
            deliveryDate = deliveryDate
        )
    }
}