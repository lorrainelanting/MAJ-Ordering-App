package com.lorrainelanting.maj.data.repository.orders

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.*
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.OrderGroup

class OrdersRepositoryImpl private constructor(
    private val orderDao: OrderDao,
    private val orderGroupDao: OrderGroupDao,
    private val cartContentDao: CartContentDao,
    private val productDao: ProductDao,
    private val userDao: UserDao,
    private val deliveryAddressDao: DeliveryAddressDao
) : OrdersRepository {

    companion object {
        private var instance: OrdersRepository? = null

        fun getInstance(
            orderDao: OrderDao,
            orderGroupDao: OrderGroupDao,
            cartContentDao: CartContentDao,
            productDao: ProductDao,
            userDao: UserDao,
            deliveryAddressDao: DeliveryAddressDao
        ): OrdersRepository {
            if (instance == null) {
                instance =
                    OrdersRepositoryImpl(
                        orderDao,
                        orderGroupDao,
                        cartContentDao,
                        productDao,
                        userDao,
                        deliveryAddressDao
                    )
            }
            return instance!!
        }
    }

    override fun getOrderList(): LiveData<List<Order>> = orderDao.getListLiveData()

    override fun getOrderGroupList(): LiveData<List<OrderGroup>> = orderGroupDao.getOrderGroupsLiveData()

    override fun getItem(id: String): LiveData<Order> = orderDao.getItemLiveData(id)

    override fun getItemByGroup(id: String): OrderGroup? = orderGroupDao.getOrderGroup(id)

    override fun save(deliveryOption: Int, status: String, deliveryDate: Long) {
        val customerInfo = userDao.get()!!
        val deliveryAddress = deliveryAddressDao.getDeliveryAddress()!!
        var orderGroupId = ""

        val orderGroup = OrderGroup.newInstance(
            deliveryOption = deliveryOption,
            status = status,
            customerName = customerInfo.fullName,
            customerContactNum = customerInfo.contactNum,
            customerStoreName = customerInfo.storeName,
            deliveryAddress = "${deliveryAddress.streetName}, ${deliveryAddress.barangay}, ${deliveryAddress.city}",
            deliveryDate = deliveryDate
        )

        // Generate orderGroup id.
        for ((i, value) in orderGroupDao.getOrderGroups().withIndex()) {
            orderGroupId = (i + 1).toString()
            orderGroup.id = orderGroupId
        }

        // Save orderGroup.
        orderGroupDao.insert(orderGroup)

        // Getting cart contents
        cartContentDao.getList().forEach { cartContent ->
            // Get product by id.
            val product = productDao.get(cartContent.productId)!!
            // Create new order
            val order = Order.newInstance(
                productId = cartContent.productId,
                productName = product.name,
                productImgUrl = product.imgUrl,
                quantity = cartContent.quantity,
                productPrice = product.price,
                productPackQty = product.packQty
            )

            for ((i, value) in orderDao.getOrders().withIndex()) {
                // Set orderGroup id as reference of order.orderGroupId.
                order.id = i.toString()
            }
            order.orderGroupId = orderGroup.id
            orderDao.insert(order)
        }
    }

    override fun update(orderGroup: OrderGroup) = orderGroupDao.update(orderGroup)

    override fun delete(id: String) = orderDao.delete(id)

    override fun getOrdersByGroup(orderGroupId: String): LiveData<List<Order>> {
        return orderDao.getOrdersByGroupId(orderGroupId)
    }

    override fun getOrderGroup(orderGroupId: String): OrderGroup {
        return orderGroupDao.getOrderGroup(orderGroupId)
    }

    override fun getOrderSizeByGroupId(orderGroupId: String): Int = orderDao.getOrderSizeByGroupId(orderGroupId)
}