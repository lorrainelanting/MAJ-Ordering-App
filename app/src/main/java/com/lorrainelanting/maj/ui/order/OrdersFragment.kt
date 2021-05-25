package com.lorrainelanting.maj.ui.order

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.databinding.FragmentOrdersBinding
import com.lorrainelanting.maj.di.Injection
import com.lorrainelanting.maj.ui.base.BaseFragment

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(),
    OrdersActiveContentAdapter.AdapterCalculation,
    OrdersPastContentAdapter.PastContentAdapterCalculation {

    lateinit var viewModel: OrderViewModel

    var adapterActiveOrders: OrdersActiveContentAdapter? = null
    var adapterPastOrders: OrdersPastContentAdapter?= null

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterActiveOrders = OrdersActiveContentAdapter(emptyList(), this)
        binding.layoutActiveOrders.rvOrdersActiveContent.adapter = adapterActiveOrders

        adapterPastOrders = OrdersPastContentAdapter(emptyList(), this)
        binding.layoutPastOrders.rvOrdersPastContent.adapter = adapterPastOrders

        viewModel.ordersContentLiveData.observe(viewLifecycleOwner) { orders ->
            adapterActiveOrders?.update(orders)
            adapterPastOrders?.update(orders)
            if (orders.isNotEmpty()) {
                binding.layoutActiveOrders.root.visibility = View.VISIBLE
                binding.layoutActiveOrders.txtActiveOrderCount.text = orders.size.toString()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = OrderViewModel(Injection.provideOrderRepository(context))
    }

    override fun getTotal(unitPrice: Double, quantity: Int): Double {
        return unitPrice * quantity
    }
}