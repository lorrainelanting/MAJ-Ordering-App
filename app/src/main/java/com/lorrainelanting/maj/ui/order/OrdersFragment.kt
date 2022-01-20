package com.lorrainelanting.maj.ui.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.OrderGroup
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.databinding.FragmentOrdersBinding
import com.lorrainelanting.maj.ui.base.BaseFragment

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(),
    OrdersContentAdapter.OrdersContentAdapterListener {

    lateinit var viewModel: OrderViewModel

    private var orderFragmentListener: OrderFragmentListener? = null
    private var orderType = COMPLETED

    var adapter: OrdersContentAdapter? = null

    companion object {
        const val ACTIVE = 0
        const val COMPLETED = 1
        const val ORDER_GROUP_ID = "ORDER_GROUP_ID"
    }

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrdersContentAdapter(emptyList(),this)
        binding.rvOrdersContent.adapter = adapter

        viewModel.orderGroupLiveData.observe(viewLifecycleOwner) { list ->
            for (orderGroup in list) {
                orderGroup.size = viewModel.getOrderSizeByGroupId(orderGroup.id)
            }
            adapter?.update(list)

//            TODO: Show empty banner base on order status filter
            //show empty banner layout
            if (list.isEmpty()) {
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

//            TODO:
        //            if (getFilteredOrders(list).isEmpty()) {
//                binding.layoutCommonBanner.imgEmptyContainer.setImageResource(R.drawable.ic_nav_orders)
//                binding.layoutCommonBanner.txtEmptyContainer.text =
//                    resources.getString(R.string.txt_empty_active_order)
//
//                binding.layoutCommonBanner.root.visibility = View.VISIBLE
//                binding.rvOrdersContent.visibility = View.GONE
//            }
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
        viewModel = OrderViewModel()
        viewModel.initializedRepositories(context)
    }

    fun setOrderFragmentListener(listener: OrderFragmentListener) {
        this.orderFragmentListener = listener
    }

    interface OrderFragmentListener {
        fun onContinueShoppingClick()
    }

    override fun onOrderCardClick(orderGroup: OrderGroup) {
        startOrderGroupActivity(orderGroup.id)
    }

    override fun onMoveToCompletedBtnClick(orderGroup: OrderGroup) {
        if (orderGroup.deliveryOption == Constants.OPTION_DELIVER) {
            orderGroup.status = Constants.STATUS_DELIVERED
        }

        if (orderGroup.deliveryOption == Constants.OPTION_PICK_UP) {
            orderGroup.status = Constants.STATUS_PICKED_UP
        }

        viewModel.updateOrderGroup(orderGroup)
        adapter?.notifyDataSetChanged()
    }

    override fun onViewOrdersBtnClick(orderGroup: OrderGroup) {
        startOrderGroupActivity(orderGroup.id)
    }

    private fun startOrderGroupActivity(orderGroupId: String) {
        val intent = Intent(context, OrdersActivity().javaClass)
        intent.putExtra(ORDER_GROUP_ID, orderGroupId)
        startActivity(intent)
    }

    private fun getFilteredOrders(contents: List<OrderGroup>): List<OrderGroup> {
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