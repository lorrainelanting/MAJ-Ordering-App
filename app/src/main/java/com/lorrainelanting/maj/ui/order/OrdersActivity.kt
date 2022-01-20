package com.lorrainelanting.maj.ui.order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.OrderGroup
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ActivityOrdersBinding
import com.lorrainelanting.maj.ui.base.BaseActivity

class OrdersActivity : BaseActivity<ActivityOrdersBinding>(),
    OrderGroupContentAdapter.OrderGroupContentAdapterCalculation {

    lateinit var viewModel: OrderViewModel
    var adapter: OrderGroupContentAdapter? = null

    companion object {
        const val ORDER_GROUP_ID = "ORDER_GROUP_ID"
    }

    override fun getLayoutId(): Int = R.layout.activity_orders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderGroupId = intent?.getStringExtra(ORDER_GROUP_ID)
        adapter = OrderGroupContentAdapter(emptyList(), this)
        binding.rvOrderGroupContent.adapter = adapter
        viewModel = OrderViewModel()
        viewModel.initializedRepositories(this)

        viewModel.orderGroupLiveData.observe(this) { list ->
            for (detail in list) {
                binding.layoutDeliveryInfo.txtCustomerName.text = detail.customerName
                binding.layoutDeliveryInfo.txtStoreName.text = detail.customerStoreName
                binding.layoutDeliveryInfo.txtCustomerMobile.text = detail.customerContactNum
                binding.layoutDeliveryInfo.txtCustomerAddress.text = detail.deliveryAddress
            }
        }

        val orderGroup: OrderGroup = viewModel.getOrderGroup(orderGroupId!!)

        viewModel.getOrdersByGroupLiveData(orderGroupId).observe(this) { orders ->
            val orderGroupContents = mutableListOf<OrderGroupContentAdapter.Content>()
            var orderStatus = ""

            for (order in orders) {
                orderStatus = orderGroup.status
                orderGroupContents.add(
                    OrderGroupContentAdapter.Content(order, orderGroup)
                )
            }

            adapter?.update(orderGroupContents)

            binding.layoutSummaryFees.txtResultSubTotal.text =
                CurrencyUtil.format(calcSubTotal(orderGroupContents))
            binding.txtResultTotalAmt.text =
                CurrencyUtil.format(calcTotal(calcSubTotal(orderGroupContents), 0.00))

            if (orderStatus == Constants.STATUS_PLACED_ORDER) {
                binding.btnMoveToCompleted.visibility = View.VISIBLE
                binding.btnReorder.visibility = View.GONE

                binding.btnMoveToCompleted.setOnClickListener {
                    onBtnMoveToCompletedClicked(orderGroup)
                }
            } else {
                binding.btnMoveToCompleted.visibility = View.GONE
                binding.btnReorder.visibility = View.VISIBLE

                binding.btnReorder.setOnClickListener {
                    reorder(orders)
                }
            }
        }
    }

    private fun onBtnMoveToCompletedClicked(orderGroup: OrderGroup) {
        if (orderGroup.deliveryOption == Constants.OPTION_DELIVER) {
            orderGroup.status = Constants.STATUS_DELIVERED
        }

        if (orderGroup.deliveryOption == Constants.OPTION_PICK_UP) {
            orderGroup.status = Constants.STATUS_PICKED_UP
        }

        viewModel.updateOrderGroup(orderGroup)
        adapter?.notifyDataSetChanged()

        binding.btnMoveToCompleted.isEnabled = false
    }

    override fun getTotal(productPrice: Double, quantity: Int): Double {
        return productPrice * quantity
    }

    private fun calcSubTotal(list: List<OrderGroupContentAdapter.Content>): Double {
        var subtotal = 0.00
        for (item in list) {
            val total = item.order.productPrice * item.order.quantity
            subtotal += total
        }
        return subtotal
    }

    private fun calcTotal(subtotal: Double, shippingFee: Double): Double {
        return subtotal + shippingFee
    }

    private fun reorder(orders: List<Order>) {
        val products = mutableListOf<Product>()
        val product = Product()
        var cartContent: CartContent
        var isReorderSuccess = false

        for (order in orders) {
            // assign product from order
            product.id = order.productId
            product.name = order.productName
            product.description = order.productDescription
            product.price = order.productPrice
            product.imgUrl = order.productImgUrl
            product.packQty = order.productPackQty
            // Product to productList
            products.add(product)

            // Create cartContent instance
            cartContent = viewModel.cartContentNewInstance(product, order.quantity)

            if (products.isNotEmpty() && order.quantity != 0) {
                isReorderSuccess = true
                // insert reorder items to cart
                viewModel.reorderItem(cartContent)
            }
        }

        notifySuccess(isReorderSuccess, getString(R.string.notify_success_added_to_cart))
    }


    private fun notifySuccess(isOperationSuccess: Boolean, message: String) {
        if (isOperationSuccess) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}