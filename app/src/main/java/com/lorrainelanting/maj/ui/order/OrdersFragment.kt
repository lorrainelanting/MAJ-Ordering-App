package com.lorrainelanting.maj.ui.order

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import com.google.android.material.tabs.TabLayout
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.databinding.FragmentOrdersBinding
import com.lorrainelanting.maj.di.Injection
import com.lorrainelanting.maj.ui.base.BaseFragment

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(),
    OrdersContentAdapter.OrdersContentAdapterCalculation,
    OrdersContentAdapter.OrdersContentAdapterListener {

    lateinit var viewModel: OrderViewModel

    private var orderFragmentListener: OrderFragmentListener? = null
    private var orderType = COMPLETED

    var adapter: OrdersContentAdapter? = null

    companion object {
        const val ACTIVE = 0
        const val COMPLETED = 1
    }

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrdersContentAdapter(emptyList(), this, this)
        binding.rvOrdersContent.adapter = adapter

        viewModel.ordersContentLiveData.observe(viewLifecycleOwner) { orders ->
            adapter?.update(orders)

//            TODO: Show empty banner base on order status filter
            //show empty banner layout
            if (orders.isEmpty()) {
                binding.layoutCommonBanner.imgEmptyContainer.setImageResource(R.drawable.ic_nav_orders)
                binding.layoutCommonBanner.txtEmptyContainer.text =
                    resources.getString(R.string.txt_empty_active_order)

                binding.layoutCommonBanner.root.visibility = View.VISIBLE
                binding.rvOrdersContent.visibility = View.GONE
            } else {
                //hide empty banner layout
                binding.layoutCommonBanner.root.visibility = View.GONE
                binding.rvOrdersContent.visibility = View.VISIBLE
            }
        }

        adapter?.setOrderStatus(binding.tabOrderStatus.selectedTabPosition)
        binding.tabOrderStatus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    COMPLETED -> {
                        adapter?.setOrderStatus(COMPLETED)
                    }
                    else -> {
                        adapter?.setOrderStatus(ACTIVE)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }
        })

        binding.layoutCommonBanner.btnContinueShopping.setOnClickListener {
            orderFragmentListener?.onContinueShoppingClick()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = OrderViewModel(Injection.provideOrderRepository(context), Injection.provideCartRepository(context))
    }

    override fun getTotal(unitPrice: Double, quantity: Int): Double {
        return unitPrice * quantity
    }

    fun setOrderFragmentListener(listener: OrderFragmentListener) {
        this.orderFragmentListener = listener
    }

    interface OrderFragmentListener {
        fun onContinueShoppingClick()
    }

    override fun onMoveToCompletedBtnClick(order: Order) {
        if (order.deliveryOption == Constants.OPTION_DELIVER) {
            order.status = Constants.STATUS_DELIVERED
        }

        if (order.deliveryOption == Constants.OPTION_PICK_UP) {
            order.status = Constants.STATUS_PICKED_UP
        }

        viewModel.orderRepository.update(order)
        adapter?.notifyDataSetChanged()
    }

    override fun onReorderBtnClick(order: Order) {
        var cartContent = viewModel.cartContentNewInstance(order.productId, order.quantity)
        viewModel.cartRepository.add(cartContent)
        Toast.makeText(context, "Item successfully added to cart.", Toast.LENGTH_SHORT).show()
    }

    private fun getFilteredOrders(contents: List<Order>): List<Order> {
        return when (orderType) {
            ACTIVE -> {
                contents.filter { order ->
                    order.status == Constants.STATUS_PLACED_ORDER
                }
            }
            COMPLETED -> {
                contents.filter { order ->
                    order.status == Constants.STATUS_DELIVERED || order.status == Constants.STATUS_PICKED_UP
                }
            }
            else -> {
                contents
            }
        }
    }
}